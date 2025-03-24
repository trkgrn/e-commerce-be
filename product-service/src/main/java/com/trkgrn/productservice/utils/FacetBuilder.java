package com.trkgrn.productservice.utils;

import co.elastic.clients.elasticsearch._types.aggregations.StringTermsAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import com.trkgrn.common.dto.productservice.response.FacetDto;
import com.trkgrn.common.dto.productservice.response.FacetValueDto;
import com.trkgrn.common.utils.Localization;
import com.trkgrn.productservice.model.index.ProductIndex;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FacetBuilder {

    private static final List<String> FACETS = List.of(ProductIndex.SIZE, ProductIndex.COLOR, ProductIndex.MATERIAL);
    private static final String FACET_DISPLAY_NAME = "facet.%s.displayName";

    public static List<FacetDto> buildFacets(SearchHits<ProductIndex> searchHits, String currentQuery) {
        List<FacetDto> facets = new ArrayList<>();
        if (searchHits.getAggregations() instanceof ElasticsearchAggregations aggregations) {
            for (String facet : FACETS) {
                String displayName = Localization.getLocalizedMessage(String.format(FACET_DISPLAY_NAME, facet));
                facets.add(buildFacet(aggregations, facet, displayName, currentQuery));
            }
        }
        return facets;
    }

    private static FacetDto buildFacet(ElasticsearchAggregations aggregations, String aggName, String displayName, String currentQuery) {
        ElasticsearchAggregation aggregation = aggregations.get(aggName);
        if (Objects.isNull(aggregation)) {
            return new FacetDto.Builder()
                    .name(displayName)
                    .values(Collections.emptyList())
                    .build();
        }

        List<FacetValueDto> values = new ArrayList<>();

        StringTermsAggregate termsAggregate = aggregation.aggregation().getAggregate().sterms();

        for (StringTermsBucket bucket : termsAggregate.buckets().array()) {
            FacetValueDto facetValueDto = new FacetValueDto.Builder()
                    .value(bucket.key().stringValue())
                    .count(bucket.docCount())
                    .query(QueryParser.addFilter(currentQuery, aggName, bucket.key().stringValue()))
                    .selected(QueryParser.parseQuery(currentQuery).getOrDefault(aggName, Collections.emptyList()).contains(bucket.key().stringValue()))
                    .build();
            values.add(facetValueDto);
        }

        return new FacetDto.Builder()
                .name(displayName)
                .values(values)
                .build();
    }

}

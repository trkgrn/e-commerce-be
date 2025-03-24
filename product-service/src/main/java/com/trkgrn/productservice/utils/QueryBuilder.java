package com.trkgrn.productservice.utils;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.trkgrn.productservice.model.index.ProductIndex;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.List;
import java.util.Map;

public class QueryBuilder {

    private static final String FUZZINESS = "AUTO";
    private static final String DOT = ".";
    private static final String AUTOCOMPLETE = "autocomplete";
    private static final String KEYWORD = "keyword";

    private static final List<String> AUTOCOMPLETE_FIELDS = List.of(
            ProductIndex.NAME + DOT + AUTOCOMPLETE,
            ProductIndex.DESCRIPTION + DOT + AUTOCOMPLETE,
            ProductIndex.COLOR + DOT + AUTOCOMPLETE,
            ProductIndex.SIZE + DOT + AUTOCOMPLETE,
            ProductIndex.MATERIAL + DOT + AUTOCOMPLETE
    );

    public static NativeQuery buildFilterQuery(String query, Pageable pageable) {
        Map<String, List<String>> filters = QueryParser.parseQuery(query);
        Criteria criteria = CriteriaBuilder.buildCriteria(filters);
        Query filterQuery = CriteriaQuery.builder(criteria).build();

        NativeQueryBuilder queryBuilder = new NativeQueryBuilder()
                .withQuery(filterQuery)
                .withPageable(pageable)
                .withSort(pageable.getSort());

        addAggregations(queryBuilder);
        return queryBuilder.build();
    }

    public static NativeQuery buildSearchQuery(String searchTerm, Pageable pageable) {
        NativeQueryBuilder queryBuilder = new NativeQueryBuilder()
                .withQuery(QueryBuilders.multiMatch()
                        .query(searchTerm)
                        .fuzziness(FUZZINESS)
                        .fields(AUTOCOMPLETE_FIELDS)
                        .build()
                        ._toQuery()
                )
                .withPageable(pageable);

        addAggregations(queryBuilder);
        return queryBuilder.build();
    }

    private static void addAggregations(NativeQueryBuilder queryBuilder) {
        queryBuilder
                .withAggregation(ProductIndex.COLOR, Aggregation.of(a -> a.terms(t -> t.field(ProductIndex.COLOR + DOT + KEYWORD))))
                .withAggregation(ProductIndex.SIZE, Aggregation.of(a -> a.terms(t -> t.field(ProductIndex.SIZE + DOT + KEYWORD))))
                .withAggregation(ProductIndex.MATERIAL, Aggregation.of(a -> a.terms(t -> t.field(ProductIndex.MATERIAL + DOT + KEYWORD))));
    }

}

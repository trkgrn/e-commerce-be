package com.trkgrn.productservice.service.impl;

import com.trkgrn.common.dto.PaginationDto;
import com.trkgrn.common.dto.productservice.response.ProductSearchResponse;
import com.trkgrn.productservice.mapper.ProductIndexMapper;
import com.trkgrn.productservice.model.index.ProductIndex;
import com.trkgrn.productservice.service.ProductSearchService;
import com.trkgrn.productservice.utils.FacetBuilder;
import com.trkgrn.productservice.utils.QueryBuilder;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    private final Logger log = Logger.getLogger(ProductSearchServiceImpl.class.getName());

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final ProductIndexMapper productIndexMapper;

    public ProductSearchServiceImpl(ElasticsearchTemplate elasticsearchTemplate, ProductIndexMapper productIndexMapper) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.productIndexMapper = productIndexMapper;
    }

    @Override
    public ProductSearchResponse filterProducts(String query, Pageable pageable) {
        return buildProductSearchResponse(QueryBuilder.buildFilterQuery(query, pageable), query, pageable);
    }

    @Override
    public ProductSearchResponse searchProducts(String searchTerm, Pageable pageable) {
        return buildProductSearchResponse(QueryBuilder.buildSearchQuery(searchTerm, pageable), Strings.EMPTY, pageable);
    }

    private ProductSearchResponse buildProductSearchResponse(NativeQuery searchQuery, String facetQuery, Pageable pageable) {
        ProductSearchResponse response = new ProductSearchResponse();
        SearchHits<ProductIndex> searchHits = elasticsearchTemplate.search(searchQuery, ProductIndex.class);
        List<ProductIndex> products = searchHits.stream().map(SearchHit::getContent).toList();

        response.setProducts(productIndexMapper.toDto(products));
        response.setSuggestions(buildSuggestions(searchHits));
        response.setFacets(FacetBuilder.buildFacets(searchHits, facetQuery));
        response.setPagination(buildPagination(pageable, searchHits));
        return response;
    }

    private List<String> buildSuggestions(SearchHits<ProductIndex> searchSuggestions) {
        Set<String> suggestions = new HashSet<>();
        searchSuggestions.getSearchHits().forEach(searchHit -> suggestions.add(searchHit.getContent().getName()));
        return suggestions.stream().toList();
    }

    private PaginationDto buildPagination(Pageable pageable, SearchHits<ProductIndex> searchHits) {
        return new PaginationDto.Builder()
                .currentPage(pageable.getPageNumber())
                .pageSize(searchHits.getSearchHits().size())
                .totalPages(pageable.getPageSize() == 0 ? 0 : (int) Math.ceil((double) searchHits.getTotalHits() / pageable.getPageSize()))
                .totalResults(searchHits.getTotalHits())
                .build();
    }

}



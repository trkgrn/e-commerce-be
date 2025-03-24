package com.trkgrn.productservice.utils;

import org.springframework.data.elasticsearch.core.query.Criteria;

import java.util.List;
import java.util.Map;

public class CriteriaBuilder {

    private static final String DOT = ".";
    private static final String KEYWORD = "keyword";

    public static Criteria buildCriteria(Map<String, List<String>> filters) {
        Criteria criteria = new Criteria();

        for (Map.Entry<String, List<String>> entry : filters.entrySet()) {
            String key = entry.getKey() + DOT + KEYWORD;
            List<String> values = entry.getValue();

            if (!values.isEmpty()) {
                Criteria subCriteria = Criteria.where(key).in(values);
                criteria = criteria.and(subCriteria);
            } else {
                Criteria subCriteria = Criteria.where(key).exists();
                criteria = criteria.and(subCriteria);
            }
        }

        return criteria;
    }

}

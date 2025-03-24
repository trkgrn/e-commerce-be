package com.trkgrn.productservice.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.*;

/**
 * The QueryParser class is used to parse filter queries in a specific format,
 * add new filters, or remove existing filters.
 * Query format: "key1:value1:key2:value2:key3:value3"
 * Example: "color:red:size:large:material:cotton"
 */
public class QueryParser {

    private static final String COLON = ":";

    /**
     * Parses the given query string and returns a Map.
     * Query format: "key1:value1:key2:value2:key3:value3"
     * Example: "color:red:size:large" -> {color=[red], size=[large]}
     *
     * @param query The query string to be parsed. Example: "color:red:size:large"
     * @return A Map containing key-value pairs. Example: {color=[red], size=[large]}
     */
    public static Map<String, List<String>> parseQuery(String query) {
        Map<String, List<String>> filters = new HashMap<>();
        if (StringUtils.isBlank(query)) {
            return filters;
        }

        String[] parts = query.split(COLON);
        String key = null;

        for (String s : parts) {
            String part = s.trim();

            if (key == null) {
                key = part;
            } else {
                filters.computeIfAbsent(key, k -> new ArrayList<>()).add(part);
                key = null;
            }
        }

        if (key != null) {
            filters.put(key, Collections.emptyList());
        }

        return filters;
    }

    /**
     * Adds a new filter to the existing query.
     * If the filter already exists, it removes the filter (toggle logic).
     * Example:
     * - Existing query: "color:red"
     * - Added filter: "size:large"
     * - Result: "color:red:size:large"
     *
     * @param query The existing query string. Example: "color:red"
     * @param key   The filter key. Example: "size"
     * @param value The filter value. Example: "large"
     * @return The query string with the new filter added or removed. Example: "color:red:size:large"
     */
    public static String addFilter(String query, String key, String value) {
        if (StringUtils.isBlank(query)) {
            return key + COLON + value;
        }

        Map<String, List<String>> filters = parseQuery(query);
        if (filters.containsKey(key) && filters.get(key).contains(value)) {
            return removeFilter(query, key, value);
        }
        return query + COLON + key + COLON + value;
    }

    /**
     * Removes a specific filter from the existing query.
     * Example:
     * - Existing query: "color:red:size:large"
     * - Removed filter: "size:large"
     * - Result: "color:red"
     *
     * @param query The existing query string. Example: "color:red:size:large"
     * @param key   The filter key to be removed. Example: "size"
     * @param value The filter value to be removed. Example: "large"
     * @return The query string with the filter removed. Example: "color:red"
     */
    public static String removeFilter(String query, String key, String value) {
        StringBuilder currentQuery = new StringBuilder(query.replace(key + COLON + value, Strings.EMPTY));
        if (!currentQuery.isEmpty()) {
            if (currentQuery.charAt(0) == COLON.charAt(0)) {
                currentQuery.deleteCharAt(0);
            }

            if (currentQuery.charAt(currentQuery.length() - 1) == COLON.charAt(0)) {
                currentQuery.deleteCharAt(currentQuery.length() - 1);
            }
            return currentQuery.toString();
        }
        return "";
    }

}

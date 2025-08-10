package com.trkgrn.common.utils;

import org.springframework.core.env.Environment;

public final class EnvironmentUtils {

    private EnvironmentUtils() {
    }

    public static String getProperty(String key) {
        Environment env = SpringContext.getBean(Environment.class);
        return env.getProperty(key);
    }

}

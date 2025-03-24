package com.trkgrn.common.utils;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Localization {

    public static final String BASE_NAME = "message";
    public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    public static String getLocalizedMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return getLocalizedMessage(key, locale);
    }

    public static String getLocalizedMessage(String key, Locale locale) {
        if (Objects.isNull(locale)) {
            locale = DEFAULT_LOCALE;
        }
        return ResourceBundle.getBundle(BASE_NAME, locale).getString(key);
    }


}

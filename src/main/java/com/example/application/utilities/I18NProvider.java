package com.example.application.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18NProvider {
    private static final String BUNDLE_NAME = "META-INF.resources.i18n.message";
    private static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(Locale.ENGLISH, Locale.GERMAN);

    public List<Locale> getProvidedLocales() {
        return SUPPORTED_LOCALES;
    }

    public static String getTranslation(String key, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
        return bundle.getString(key);
    }
}
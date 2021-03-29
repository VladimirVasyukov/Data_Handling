package com.epam.datahandling.utils;

import java.util.ResourceBundle;

public final class RegexProvider {
    public static final ResourceBundle REG_EX_BUNDLE = ResourceBundle.getBundle("regular_expressions");

    private RegexProvider() {
    }

    public static String get(String key) {
        return REG_EX_BUNDLE.getString(key);
    }

}

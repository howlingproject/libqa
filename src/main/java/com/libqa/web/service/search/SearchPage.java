package com.libqa.web.service.search;

public enum SearchPage {
    QA("qa"),
    SPACE("space"),
    WIKI("wiki");

    final String pageName;

    SearchPage(String pageName) {
        this.pageName = pageName;
    }

    public static String findPageName(String pageName) {
        for (SearchPage each : SearchPage.values()) {
            if (pageName.equals(each.pageName)) {
                return pageName;
            }
        }

        throw new IllegalArgumentException("Cannot found page name.");
    }
}

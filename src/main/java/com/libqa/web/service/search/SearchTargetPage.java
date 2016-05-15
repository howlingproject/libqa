package com.libqa.web.service.search;

public enum SearchTargetPage {
    QA("qa"),
    SPACE("space"),
    WIKI("wiki");

    final String page;

    SearchTargetPage(String page) {
        this.page = page;
    }

    public static boolean isValidKey(String pageKey) {
        for (SearchTargetPage each : SearchTargetPage.values()) {
            if (pageKey.equals(each.page)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInValidKey(String pageKey) {
        return !isValidKey(pageKey);
    }
}

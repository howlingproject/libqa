package com.libqa.application.enums;

public enum SearchTargetPage {
    QA("qa", "Q&A"),
    SPACE("space", "Space"),
    WIKI("wiki", "Wiki"),
    INVALID("invalid", "Invalid");

    final String page;
    final String desc;

    SearchTargetPage(String page, String desc) {
        this.page = page;
        this.desc = desc;
    }

    public static SearchTargetPage get(String page) {
        for (SearchTargetPage each : SearchTargetPage.values()) {
            if(page.equals(each.getPage())) {
                return each;
            }
        }
        return INVALID;
    }

    public boolean isInValidPage() {
        return get(page) == INVALID;
    }

    public String getPage() {
        return page;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isQA() {
        return this == QA;
    }

    public boolean isSpace() {
        return this == SPACE;
    }
}

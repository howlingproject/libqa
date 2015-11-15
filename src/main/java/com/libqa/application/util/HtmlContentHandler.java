package com.libqa.application.util;

import com.twitter.Autolink;

public class HtmlContentHandler {
    private static final Autolink AUTO_LINK = new Autolink();
    private String text;

    public HtmlContentHandler(String text) {
        this.text = text;
    }

    public HtmlContentHandler urlWithLink() {
        AUTO_LINK.setUrlTarget("_blank");
        text = AUTO_LINK.autoLink(text);
        return this;
    }

    public HtmlContentHandler nl2br() {
        text = text.replaceAll("(\\r\\n|\\n)", "<br />");
        return this;
    }

    public String parse() {
        return text;
    }
}


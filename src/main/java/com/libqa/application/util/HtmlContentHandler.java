package com.libqa.application.util;

import com.twitter.Autolink;

public class HtmlContentHandler {
    private String text;

    public HtmlContentHandler(String text) {
        this.text = text;
    }

    public HtmlContentHandler urlWithLink() {
        Autolink autoLink = new Autolink();
        autoLink.setUrlTarget("_blank");
        text = autoLink.autoLink(text);
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


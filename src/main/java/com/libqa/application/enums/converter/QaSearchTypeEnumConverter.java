package com.libqa.application.enums.converter;

import com.libqa.application.enums.QaSearchType;

import java.beans.PropertyEditorSupport;

/**
 * Created by yong on 2016. 8. 19..
 */
public class QaSearchTypeEnumConverter extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        String capitalized = text.toUpperCase();
        QaSearchType qaSearchType = QaSearchType.valueOf(capitalized);
        setValue(qaSearchType);
    }
}

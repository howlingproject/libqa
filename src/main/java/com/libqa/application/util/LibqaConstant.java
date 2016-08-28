package com.libqa.application.util;

/**
 * @Author : yion
 * @Date : 2016. 4. 17.
 * @Description : 상수 정의 - 업무명_상수명_타입 : WIKI_PAGE_SIZE
 */
public class LibqaConstant {

    public static final String DEFAULT_RETURN_URL = "/index";

    public static final Integer PAGE_START_INDEX = 0;
    public static final Integer SPACE_PAGE_SIZE = 10;
    public static final Integer SPACE_WIKI_SIZE = 10;

    public static final String SORT_TYPE_TITLE = "title";
    public static final String SORT_TYPE_DATE = "updateDate";

    public static final Integer ZERO = 0;

    public class ErrorPagePath {
        public static final String DEFAULT_ERROR = "/error";
        public static final String ERROR_401 = DEFAULT_ERROR + "/401";
        public static final String ERROR_403 = DEFAULT_ERROR + "/403";
        public static final String ERROR_404 = DEFAULT_ERROR + "/404";
        public static final String ERROR_500 = DEFAULT_ERROR + "/500";
    }

}

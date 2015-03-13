package com.libqa.web.form;

import com.libqa.domain.Keyword;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
@Data
public class QaCreateForm {
    @NotEmpty
    private String title;

    @NotEmpty
    private String contents;

    @NotEmpty
    private String contentsMarkup;

    @NotEmpty
    private Integer insertUserId;

    private List<Keyword> keywords;
}

package com.libqa.web.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by yion on 2015. 3. 8..
 */
@Data
public class SpaceCreateForm {

    @NotEmpty
    private String description;

    @NotEmpty
    private String descriptionMarkup;

    @NotEmpty
    private Integer insertUserId;

    @NotEmpty
    @Size(max = 80)
    private String title;

    @Size(max = 40)
    private String titleImage;

    @Size(max = 50)
    private String titleImagePath;


    @Size(max = 20)
    private String layoutType;




}

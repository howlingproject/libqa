package com.libqa.web.view.space;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.libqa.application.enums.SpaceView;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.Space;
import com.libqa.web.domain.SpaceAccessUser;
import lombok.Getter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author : yion
 * @Date : 2015. 6. 21.
 * @Description :
 */
@Getter
public class SpaceMain {

    private Integer spaceId;
    private String title;
    private String titleImage;
    private String titleImagePath;
    private String description;
    private String descriptionMarkup;
    private SpaceView layoutType;
    private boolean isPrivate;
    private boolean isDeleted;
    private String insertDate;
    private String updateDate;
    private Integer insertUserId;
    private String insertUserNick;
    private Integer updateUserId;
    private String updateUserNick;
    private List<SpaceAccessUser> spaceAccessUsers;

    private List<Keyword> keywords;
    private int wikiCount;

    public SpaceMain(Space space, int wikiCount, List keywords) {
        this.spaceId = space.getSpaceId();
        this.title = space.getTitle();
        this.titleImage = space.getTitleImage();
        this.titleImagePath = space.getTitleImagePath();
        this.description = space.getDescription();
        this.descriptionMarkup = space.getDescriptionMarkup();
        this.layoutType = space.getLayoutType();
        this.isPrivate = space.isPrivate();
        this.isDeleted = space.isDeleted();
        this.insertDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(space.getInsertDate());
        this.updateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(space.getUpdateDate());
        this.insertUserId = space.getInsertUserId();
        this.insertUserNick = space.getInsertUserNick();
        this.updateUserId = space.getUpdateUserId();
        this.updateUserNick = space.getUpdateUserNick();
        this.spaceAccessUsers = space.getSpaceAccessUsers();
        this.wikiCount = wikiCount;
        this.keywords = keywords;
    }

}

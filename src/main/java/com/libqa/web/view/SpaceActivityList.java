package com.libqa.web.view;

import com.libqa.web.domain.Activity;
import com.libqa.web.domain.User;
import lombok.Getter;

/**
 * @Author : yion
 * @Date : 2015. 10. 11.
 * @Description :
 */

@Getter
public class SpaceActivityList {

    private Activity activity;
    private User user;

    public SpaceActivityList(Activity activity, User user) {
        this.activity = activity;
        this.user = user;
    }
}

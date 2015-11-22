package com.libqa.web.service.user;

import com.libqa.web.domain.User;
import com.libqa.web.domain.UserKeyword;

import java.util.List;

/**
 * @Author : yion
 * @Date : 2015. 11. 10.
 * @Description :
 */
public interface UserKeywordService {

    List<UserKeyword> findByUserAndIsDeleted(User user);
}

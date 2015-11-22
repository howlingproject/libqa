package com.libqa.web.service.user;

import com.libqa.web.domain.User;
import com.libqa.web.domain.UserKeyword;
import com.libqa.web.repository.UserKeywordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : yion
 * @Date : 2015. 11. 10.
 * @Description :
 */
@Slf4j
@Service
public class UserKeywordServiceImpl implements UserKeywordService {

    @Autowired
    private UserKeywordRepository userKeywordRepository;

    @Override
    public List<UserKeyword> findByUserAndIsDeleted(User user) {
        List<UserKeyword> userKeywords = userKeywordRepository.findByUserAndIsDeleted(user, false);
        return userKeywords;
    }
}

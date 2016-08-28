package com.libqa.web.view.user;

import com.libqa.web.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author : yion
 * @Date : 2016. 8. 28.
 * @Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserList {

    private List<User> userList;

}

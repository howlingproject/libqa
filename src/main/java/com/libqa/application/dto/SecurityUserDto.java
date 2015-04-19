package com.libqa.application.dto;

import lombok.Data;

/**
 * @Author : yion
 * @Date : 2015. 4. 19.
 * @Description :
 */
@Data
public class SecurityUserDto {
    private String userEmail;
    private String role;
    private String userIp;
    private boolean isAuthenticated;
}

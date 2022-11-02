package com.ditstek.userlogin.dto;

import lombok.Data;

@Data
public class ChangePassword {

    private String email;
    private String password;

    private String confirmPassword;
}

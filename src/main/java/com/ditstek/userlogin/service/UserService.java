package com.ditstek.userlogin.service;

import com.ditstek.userlogin.dto.ChangePassword;
import com.ditstek.userlogin.dto.loginDto.EmailLoginDto;
import com.ditstek.userlogin.dto.loginDto.MobileNoLoginDto;
import com.ditstek.userlogin.dto.loginDto.UsernameLoginDto;
import com.ditstek.userlogin.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<Object> saveUser(User user) throws Exception;

    ResponseEntity<Object> emailLoginValidate(EmailLoginDto emailLoginDto) throws Exception;

    ResponseEntity<Object> usernameLoginValidate(UsernameLoginDto usernameLoginDto) throws Exception;

    ResponseEntity<Object> mobileNoLoginValidate(MobileNoLoginDto mobileNoLoginDto) throws Exception;

    ResponseEntity<Object> changePassword(ChangePassword changePassword) throws Exception;

    ResponseEntity<Object> updateUserById(Long id, User user) throws Exception;
}

package com.ditstek.userlogin.controller;

import com.ditstek.userlogin.dto.loginDto.EmailLoginDto;
import com.ditstek.userlogin.dto.loginDto.MobileNoLoginDto;
import com.ditstek.userlogin.dto.loginDto.UsernameLoginDto;
import com.ditstek.userlogin.entity.User;
import com.ditstek.userlogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/updateUser/1")
    public ResponseEntity<Object> updateUser(@PathVariable("id") Long id, @RequestBody User user) throws Exception {
        userService.updateUserById(id, user);
        return null;
    }

    @PostMapping("/email/userLogin")
    public ResponseEntity<Object> userLoginByEmail(@RequestBody EmailLoginDto emailLoginDto) throws Exception {
        return userService.emailLoginValidate(emailLoginDto);
    }

    @PostMapping("/userName/userLogin")
    public ResponseEntity<Object> userLoginByUserName(@RequestBody UsernameLoginDto usernameLoginDto) throws Exception {
        return userService.usernameLoginValidate(usernameLoginDto);
    }

    @PostMapping("/mobileNo/userLogin")
    public ResponseEntity<Object> userLoginByMobileNo(@RequestBody MobileNoLoginDto mobileNoLoginDto) throws Exception {
        return userService.mobileNoLoginValidate(mobileNoLoginDto);
    }
}

package com.ditstek.userlogin.controller;

import com.ditstek.userlogin.dto.ChangePassword;
import com.ditstek.userlogin.entity.User;
import com.ditstek.userlogin.helper.SendEmail;
import com.ditstek.userlogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user/userRegister")
    public ResponseEntity<Object> userRegister(@RequestBody User user) throws Exception {
        return userService.saveUser(user);
    }

    @PostMapping("/user/forgrtPassword/{email}")
    public String forgetPassword(@PathVariable("email") String email) {
        String url = "http://localhost:8080/changePassword";
        SendEmail sm = new SendEmail();
        sm.sendEmail(email, url);
        return "email sent";
    }

    @PostMapping("/user/forgetPassword")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePassword changePassword) throws Exception {
        return userService.changePassword(changePassword);
    }
}

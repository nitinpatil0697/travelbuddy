package com.nitin.userservice.controller;

import com.nitin.userservice.api.request.RegisterUser;
import com.nitin.userservice.api.response.GeneralResponse;
import com.nitin.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("register")
    public ResponseEntity<GeneralResponse> registerUser(RegisterUser registerUser) {
        return userService.registerUser(registerUser);
    }
}

package com.nitin.userservice.controller;

import com.nitin.userservice.api.request.LoginRequest;
import com.nitin.userservice.api.request.RegisterUser;
import com.nitin.userservice.api.response.GeneralResponse;
import com.nitin.userservice.model.UserEntity;
import com.nitin.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Controller Method of Registering the User
     */
    @PostMapping("register")
    public ResponseEntity<GeneralResponse> registerUser(RegisterUser registerUser) {
        return userService.registerUser(registerUser);
    }

    /**
     * Controller Method of Login the User
     */
    @PostMapping("login")
    public ResponseEntity<GeneralResponse> loginUser(@RequestBody LoginRequest loginRequest){
        return userService.loginUser(loginRequest);
    }

    /**
     * Controller Method of Update the User Details
     */
    @PutMapping("updateUser/{userId}")
    public ResponseEntity<GeneralResponse> updateUserDetails(
            @PathVariable Integer userId, @RequestBody UserEntity userData) {
        return userService.updateUserDetails(userId, userData);
    }

    /**
     * Controller Method of Delete the User
     */
    @DeleteMapping("deleteUser/{userId}")
    public ResponseEntity<GeneralResponse> deleteUser(@PathVariable Integer userId) {
        return userService.deleteUserById(userId);
    }
}

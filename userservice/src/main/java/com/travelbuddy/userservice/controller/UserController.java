package com.travelbuddy.userservice.controller;

import com.travelbuddy.userservice.api.request.LoginRequest;
import com.travelbuddy.userservice.api.request.RegisterUser;
import com.travelbuddy.userservice.api.response.GeneralResponse;
import com.travelbuddy.userservice.model.UserEntity;
import com.travelbuddy.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<GeneralResponse> deleteUser(@PathVariable(name="userid") Integer id) {
        return userService.deleteUserById(id);
    }
}

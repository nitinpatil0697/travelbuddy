package com.travelbuddy.userservice.service;

import com.travelbuddy.userservice.api.request.LoginRequest;
import com.travelbuddy.userservice.api.request.RegisterUser;
import com.travelbuddy.userservice.api.response.GeneralResponse;
import com.travelbuddy.userservice.model.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ResponseEntity<GeneralResponse> registerUser(RegisterUser registerUser);

    ResponseEntity<GeneralResponse> loginUser(LoginRequest loginRequest);

    ResponseEntity<GeneralResponse> updateUserDetails(Integer userId, UserEntity userData);

    ResponseEntity<GeneralResponse> deleteUserById(Integer userId);
}

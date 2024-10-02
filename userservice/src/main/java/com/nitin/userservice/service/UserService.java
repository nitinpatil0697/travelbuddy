package com.nitin.userservice.service;

import com.nitin.userservice.api.request.LoginRequest;
import com.nitin.userservice.api.request.RegisterUser;
import com.nitin.userservice.api.response.GeneralResponse;
import com.nitin.userservice.model.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ResponseEntity<GeneralResponse> registerUser(RegisterUser registerUser);

    ResponseEntity<GeneralResponse> loginUser(LoginRequest loginRequest);

    ResponseEntity<GeneralResponse> updateUserDetails(Integer userId, UserEntity userData);

    ResponseEntity<GeneralResponse> deleteUserById(Integer userId);
}

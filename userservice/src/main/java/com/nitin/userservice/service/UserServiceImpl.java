package com.nitin.userservice.service;

import com.nitin.userservice.api.request.RegisterUser;
import com.nitin.userservice.api.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public class UserServiceImpl implements UserService {

    /**
     * Register the User
     *
     * @param registerUser
     * @return
     */
    @Override
    public ResponseEntity<GeneralResponse> registerUser(RegisterUser registerUser) {
        GeneralResponse registerResponse = new GeneralResponse();
        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }
}

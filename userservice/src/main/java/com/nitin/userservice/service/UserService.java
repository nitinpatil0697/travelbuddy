package com.nitin.userservice.service;

import com.nitin.userservice.api.request.RegisterUser;
import com.nitin.userservice.api.response.GeneralResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public ResponseEntity<GeneralResponse> registerUser(RegisterUser registerUser);
}

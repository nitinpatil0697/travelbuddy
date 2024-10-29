package com.travelbuddy.userservice.service;

import com.travelbuddy.userservice.api.request.LoginRequest;
import com.travelbuddy.userservice.api.request.RegisterUser;
import com.travelbuddy.userservice.api.response.GeneralResponse;
import com.travelbuddy.userservice.constants.UserServiceConstants;
import com.travelbuddy.userservice.model.UserEntity;
import com.travelbuddy.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    /**
     * Register the User
     */
    @Override
    public ResponseEntity<GeneralResponse> registerUser(RegisterUser registerUser) {
        log.info("registerUser REQ: {}", registerUser);
        log.info("Registering user process started.");
        GeneralResponse registerResponse = new GeneralResponse();
        try{
            if (!this.validateRegisterRequest(registerUser)) {
                throw new Exception("VALIDATION_FAILED");
            }

            if (userRepository.findByEmail(registerUser.getEmail()) != null) {
                throw new Exception("USER_ALREADY_REGISTERED");
            }

            UserEntity newUser = new UserEntity();
            LocalDateTime currentDateTime = LocalDateTime.now();
            newUser.setFirstName(registerUser.getFirstName());
            newUser.setLastName(registerUser.getLastName());
            newUser.setEmail(registerUser.getEmail());
            newUser.setPassword(registerUser.getPassword());
            newUser.setCreatedAt(currentDateTime);
            newUser.setEnabled(true);
            newUser.setPhone(registerUser.getPhone());
            userRepository.save(newUser);

            registerResponse.setStatus(UserServiceConstants.SUCCESS);
            registerResponse.setMessage("User registered successfully");
        } catch (Exception e) {
            registerResponse.setStatus(UserServiceConstants.FAILURE);
            registerResponse.setMessage(e.getMessage());
        }
        log.info("registerUser RES: {}", registerResponse);

        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    /**
     * To Process the Login
     */
    @Override
    public ResponseEntity<GeneralResponse> loginUser(LoginRequest loginRequest) {
        UserEntity userData = userRepository.findByEmail(loginRequest.getEmail());
        GeneralResponse loginResponse = new GeneralResponse();
        loginResponse.setStatus(UserServiceConstants.FAILURE);
        if (userData == null) {
            loginResponse.setMessage("No user found with email.");
        } else {
            if (!userData.getPassword().equals(loginRequest.getPassword())) {
                loginResponse.setMessage("Invalid password");
            } else {
                loginResponse.setStatus(UserServiceConstants.SUCCESS);
                loginResponse.setMessage("User logged in successfully");
                HashMap<String, String> result = new HashMap<>();
                result.put("name", userData.getFirstName());
                result.put("userEmail", userData.getEmail());
                loginResponse.setResult(result);
            }
        }
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    /**
     * To Update the User details
     */
    @Override
    public ResponseEntity<GeneralResponse> updateUserDetails(Integer userId, UserEntity userData) {
        GeneralResponse response = new GeneralResponse();
        Optional<UserEntity> existingUsers = userRepository.findById(userId);
        if (existingUsers.isPresent()) {
            UserEntity existingUser = existingUsers.get();
            existingUser.setFirstName(userData.getFirstName());
            existingUser.setLastName(userData.getLastName());
            existingUser.setEmail(userData.getEmail());
            existingUser.setPhone(userData.getPhone());
            existingUser.setEnabled(userData.getEnabled());
            userRepository.save(existingUser);
        }
        response.setStatus(UserServiceConstants.SUCCESS);
        response.setMessage("Updated Data successfully");
        response.setResult(userData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * To Delete the User
     */
    @Override
    public ResponseEntity<GeneralResponse> deleteUserById(Integer userId) {
        GeneralResponse response = new GeneralResponse();
        Optional<UserEntity> existingUsers = userRepository.findById(userId);
        if (existingUsers.isPresent()) {
            UserEntity existingUser = existingUsers.get();
            existingUser.setIsDeleted(true);
            response.setStatus(UserServiceConstants.SUCCESS);
            response.setMessage("Deleted Data successfully");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Validate the Registration of new User
     */
    public boolean validateRegisterRequest(RegisterUser registerUser) {
        return !isNull(registerUser.getFirstName()) &&
                !isNull(registerUser.getLastName()) &&
                !isNull(registerUser.getEmail()) &&
                !isNull(registerUser.getPassword());
    }
}

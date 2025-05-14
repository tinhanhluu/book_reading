package com.book_reading.controller;

import com.book_reading.dto.request.UserCreationRequest;
import com.book_reading.dto.request.UserUpdateRequest;
import com.book_reading.dto.response.ApiResponse;
import com.book_reading.dto.response.UserResponse;
import com.book_reading.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> userRegistration(@RequestBody UserCreationRequest request){
        return ApiResponse.<UserResponse>builder()
                .message("User registration successful")
                .data(userService.registerUser(request))
                .build();
    }

    @GetMapping("/info")
    public ApiResponse<UserResponse> getInfo(){
        return ApiResponse.<UserResponse>builder()
                .message("Get info successful")
                .data(userService.getInfo())
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers(){
        return ApiResponse.<List<UserResponse>>builder()
                .message("Get all users successful")
                .data(userService.getAllUsers())
                .build();
    }

    @PatchMapping("/update")
    public ApiResponse<UserResponse> updateInfo(@RequestBody UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .message("Update user successful")
                .data(userService.updateInfo(request))
                .build();
    }
}

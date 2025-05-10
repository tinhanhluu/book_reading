package com.book_reading.controller;

import com.book_reading.dto.request.UserCreationRequest;
import com.book_reading.dto.request.UserUpdateRequest;
import com.book_reading.dto.response.ApiResponse;
import com.book_reading.dto.response.UserResponse;
import com.book_reading.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    public ApiResponse<UserResponse> getInfo(){
        return ApiResponse.<UserResponse>builder()
                .message("Get info successful")
                .data(userService.getInfo())
                .build();
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateInfo(@RequestBody UserUpdateRequest request){
        return ResponseEntity.ok("Update successful");
    }
}

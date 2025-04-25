package com.book_reading.controller;

import com.book_reading.dto.request.AuthenticationRequest;
import com.book_reading.dto.response.ApiResponse;
import com.book_reading.dto.response.AuthenticationResponse;
import com.book_reading.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> loginProcess(AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .message("You login successfully")
                .data(authenticationService.authenticate(request))
                .build();
    }
}

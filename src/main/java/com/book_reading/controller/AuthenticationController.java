package com.book_reading.controller;

import com.book_reading.dto.request.AuthenticationRequest;
import com.book_reading.dto.request.IntrospectRequest;
import com.book_reading.dto.response.ApiResponse;
import com.book_reading.dto.response.AuthenticationResponse;
import com.book_reading.dto.response.IntrospectResponse;
import com.book_reading.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> loginProcess(@RequestBody AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .message("You login successfully")
                .data(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/google")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> loginWithGoogle(@RequestBody IntrospectRequest request) {
        AuthenticationResponse response = authenticationService.authenticateWithGoogle(request.getToken());
        return ResponseEntity.ok(new ApiResponse<>(200, "Login with Google successful", response));
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .data(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.builder()
                .message("You logout successfully")
                .build();
    }
}

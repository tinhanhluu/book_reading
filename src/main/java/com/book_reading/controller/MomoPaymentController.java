package com.book_reading.controller;

import com.book_reading.dto.request.CreateMomoPaymentRequest;
import com.book_reading.dto.response.MomoApiResponse;
import com.book_reading.service.MomoPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/momo")
@RequiredArgsConstructor
public class MomoPaymentController {

    private final MomoPaymentService momoPaymentService;

    @PostMapping("/create-link")
    public ResponseEntity<?> createLink(@RequestBody CreateMomoPaymentRequest request) {
        try {
            MomoApiResponse response = momoPaymentService.createPaymentLink(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}

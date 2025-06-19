package com.book_reading.dto.response;

import lombok.Data;

@Data
public class MomoApiResponse {
    private String payUrl;
    private String deeplink;
    private String errorCode;
    private String message;
}

package com.book_reading.dto.request;

import lombok.Data;

@Data
public class CreateMomoPaymentRequest {
    private String orderId;
    private String amount;
    private String orderInfo;
}

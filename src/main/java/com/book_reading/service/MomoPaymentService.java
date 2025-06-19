package com.book_reading.service;

import com.book_reading.config.MomoProperties;
import com.book_reading.dto.request.CreateMomoPaymentRequest;
import com.book_reading.dto.response.MomoApiResponse;
import com.book_reading.util.SignatureUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MomoPaymentService {
    MomoProperties momoProps;
    RestTemplate restTemplate = new RestTemplate();

    public MomoApiResponse createPaymentLink(CreateMomoPaymentRequest request) throws Exception {
        String requestId = UUID.randomUUID().toString();
        String orderId = request.getOrderId();

        Map<String, String> rawData = new LinkedHashMap<>();
        rawData.put("accessKey", momoProps.getAccessKey());
        rawData.put("amount", request.getAmount());
        rawData.put("extraData", "");
        rawData.put("ipnUrl", momoProps.getNotifyUrl());
        rawData.put("orderId", orderId);
        rawData.put("orderInfo", request.getOrderInfo());
        rawData.put("partnerCode", momoProps.getPartnerCode());
        rawData.put("redirectUrl", momoProps.getReturnUrl());
        rawData.put("requestId", requestId);
        rawData.put("requestType", "captureWallet");

        // build raw signature
        StringBuilder rawSignature = new StringBuilder();
        for (Map.Entry<String, String> entry : rawData.entrySet()) {
            if (rawSignature.length() > 0) rawSignature.append("&");
            rawSignature.append(entry.getKey()).append("=").append(entry.getValue());
        }

        String signature = SignatureUtil.signSHA256(rawSignature.toString(), momoProps.getSecretKey());

        // create final payload
        Map<String, String> payload = new HashMap<>(rawData);
        payload.put("signature", signature);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(payload, headers);

        ResponseEntity<MomoApiResponse> response = restTemplate.postForEntity(
                momoProps.getEndpoint(), httpEntity, MomoApiResponse.class);

        return response.getBody();
    }
}

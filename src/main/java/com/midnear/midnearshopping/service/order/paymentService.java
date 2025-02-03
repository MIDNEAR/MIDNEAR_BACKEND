package com.midnear.midnearshopping.service.order;

import com.midnear.midnearshopping.domain.dto.payment.PaymentDbDTO;
import com.midnear.midnearshopping.mapper.order.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class paymentService {
    private final PaymentMapper paymentMapper;
    public void updatePayment(PaymentDbDTO paymentDbDTO){
        paymentMapper.updatePayment(paymentDbDTO);
    }
}

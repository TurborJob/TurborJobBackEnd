package com.turborvip.core.domain.adapter.web.rest;

import com.turborvip.core.domain.adapter.web.base.RestData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
@Validated
public interface PaymentResource {

    @PostMapping("all/payment/vnpay")
    ResponseEntity<RestData<?>> requestPaymentVNPay(HttpServletRequest request, HttpServletResponse response);
}

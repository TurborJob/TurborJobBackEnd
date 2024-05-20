package com.turborvip.core.domain.adapter.web.rest.impl;

import com.turborvip.core.domain.adapter.web.base.RestApiV1;
import com.turborvip.core.domain.adapter.web.base.RestData;
import com.turborvip.core.domain.adapter.web.base.VsResponseUtil;
import com.turborvip.core.domain.adapter.web.rest.PaymentResource;
import com.turborvip.core.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestApiV1
public class PaymentResourceImpl implements PaymentResource {

    @Autowired
    private PaymentService paymentService;

    @Override
    public ResponseEntity<RestData<?>> requestPaymentVNPay(HttpServletRequest request, HttpServletResponse response) {
        try {
            return VsResponseUtil.ok(paymentService.doPostVnPay(request,response));
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

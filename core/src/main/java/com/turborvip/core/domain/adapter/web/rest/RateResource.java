package com.turborvip.core.domain.adapter.web.rest;

import com.turborvip.core.domain.adapter.web.base.RestData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Validated
public interface RateResource {

    @PostMapping("/business-worker/get-rate")
    ResponseEntity<RestData<?>> getRate(HttpServletRequest request, @RequestBody Map<String, Object> requestBody);

    @PostMapping("/business-worker/rate")
    ResponseEntity<RestData<?>> rateUser(HttpServletRequest request, @RequestBody Map<String, Object> requestBody);
}

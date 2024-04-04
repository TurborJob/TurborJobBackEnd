package com.turborvip.core.domain.adapter.web.rest;

import com.turborvip.core.domain.adapter.web.base.RestData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface DemoResource {
    @GetMapping("/all/test")
    ResponseEntity<?> test(HttpServletRequest request);

    @GetMapping("/user/demo-auth")
    ResponseEntity<?> demoAuth(HttpServletRequest request);

    @GetMapping("/all/demo-throw-exception")
    void demoThrowException(HttpServletRequest request);

    @GetMapping("/all/demo-send-data")
    ResponseEntity<RestData<?>> demoSendData(HttpServletRequest request);
}

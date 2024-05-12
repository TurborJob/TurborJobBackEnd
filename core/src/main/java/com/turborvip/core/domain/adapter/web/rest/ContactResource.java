package com.turborvip.core.domain.adapter.web.rest;

import com.turborvip.core.domain.adapter.web.base.RestData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Validated
public interface ContactResource {
    @PostMapping("/user-only/create-contact")
    ResponseEntity<RestData<?>> createContact(HttpServletRequest request, @RequestBody Map<String, Object> requestBody);

    @PostMapping("/admin/reply-contact")
    ResponseEntity<RestData<?>> replyContact(HttpServletRequest request, @RequestBody Map<String, Object> requestBody);

    @GetMapping("/admin/get-all-contact")
    ResponseEntity<RestData<?>> getAllContact(HttpServletRequest request, @RequestBody Map<String, Object> requestBody);
}

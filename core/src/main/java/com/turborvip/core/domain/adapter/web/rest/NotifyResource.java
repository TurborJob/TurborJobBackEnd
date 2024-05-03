package com.turborvip.core.domain.adapter.web.rest;

import com.turborvip.core.domain.adapter.web.base.RestData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Validated
public interface NotifyResource {

    @PostMapping("business-worker/get-num-notify-unread")
    ResponseEntity<RestData<?>> getNumNotifyUnReadByUser(HttpServletRequest request);

    @PostMapping("business-worker/mark-all-notify-read")
    ResponseEntity<RestData<?>> markAllNotifyIsReadByUser(HttpServletRequest request);

    @PostMapping("business-worker/get-notify-user")
    ResponseEntity<RestData<?>> getNotifyByUser(HttpServletRequest request, @RequestBody Map<String, Object> requestBody);
}

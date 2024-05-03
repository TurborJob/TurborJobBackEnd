package com.turborvip.core.domain.adapter.web.rest.impl;

import com.turborvip.core.domain.adapter.web.base.RestApiV1;
import com.turborvip.core.domain.adapter.web.base.RestData;
import com.turborvip.core.domain.adapter.web.base.VsResponseUtil;
import com.turborvip.core.domain.adapter.web.rest.NotifyResource;
import com.turborvip.core.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestApiV1
public class NotifyResourceImpl implements NotifyResource {

    @Autowired
    private NotificationService notificationService;

    @Override
    public ResponseEntity<RestData<?>> getNumNotifyUnReadByUser(HttpServletRequest request) {
        try {
            return VsResponseUtil.ok("Get number notify unread successfully!", notificationService.getNumNotifyPushUnReadByUser(request));
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> markAllNotifyIsReadByUser(HttpServletRequest request) {
        try {
            notificationService.markAllNotifyIsReadByUser(request);
            return VsResponseUtil.ok("Get number notify unread successfully!");
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> getNotifyByUser(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            int page = (int) requestBody.get("page");
            int size = (int) requestBody.get("size");
            return VsResponseUtil.ok("Get number notify unread successfully!",notificationService.getNotifyByUser(request,page,size));
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

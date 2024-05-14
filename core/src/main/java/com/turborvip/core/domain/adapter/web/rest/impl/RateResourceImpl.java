package com.turborvip.core.domain.adapter.web.rest.impl;

import com.turborvip.core.domain.adapter.web.base.RestApiV1;
import com.turborvip.core.domain.adapter.web.base.RestData;
import com.turborvip.core.domain.adapter.web.base.VsResponseUtil;
import com.turborvip.core.domain.adapter.web.rest.RateResource;
import com.turborvip.core.service.RatingHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestApiV1
public class RateResourceImpl implements RateResource {

    @Autowired
    private RatingHistoryService ratingHistoryService;

    @Override
    public ResponseEntity<RestData<?>> getRate(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            int page = (int) requestBody.get("page");
            int size = (int) requestBody.get("size");
            return VsResponseUtil.ok("Get rate successfully!", ratingHistoryService.getRating(request, page, size));
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> rateUser(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            int valueRate = (int) requestBody.get("rateValue");
            String note = (String) requestBody.get("note");
            int toUserId = (int) requestBody.get("toUser");
            int rateId = (int) requestBody.get("rateId");
            ratingHistoryService.rateUser(request, (float) valueRate, note, (long) toUserId, (long) rateId);
            return VsResponseUtil.ok("Rate successfully!");
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}

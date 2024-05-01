package com.turborvip.core.service;

import com.turborvip.core.domain.http.response.RatingHistoryResponse;
import com.turborvip.core.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface RatingHistoryService {
    RatingHistoryResponse getRating(HttpServletRequest request, int page, int size) throws Exception;

    void rateUser(HttpServletRequest request, float value, String note, long toUser) throws Exception;
}

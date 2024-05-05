package com.turborvip.core.domain.adapter.web.rest.impl;

import com.turborvip.core.domain.adapter.web.base.RestApiV1;
import com.turborvip.core.domain.adapter.web.base.VsResponseUtil;
import com.turborvip.core.domain.adapter.web.rest.UserResource;
import com.turborvip.core.domain.http.request.UpdateProfileRequest;
import com.turborvip.core.service.UserService;
import com.turborvip.core.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RestApiV1
@RequiredArgsConstructor
@Component("UserResourceImpl")
public class UserResourceImpl implements UserResource {
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<?> create(User user, HttpServletRequest request) {
        try {
            return VsResponseUtil.ok(null, userService.create(user, request));
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getRoleName(HttpServletRequest request) {
        try {
            //String phone = (String) requestBody.get("phone");
            return VsResponseUtil.ok("Get role success", userService.getRoleName(request));
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateProfile(HttpServletRequest request, UpdateProfileRequest updateProfileRequest) {
        try {
            return VsResponseUtil.ok("Update profile success", userService.updateProfile(request, updateProfileRequest));

        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateProfile(HttpServletRequest request) {
        try {
            userService.updateBusiness(request);
            return VsResponseUtil.ok("Update business success");

        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getBusinessStatistic(HttpServletRequest request) {
        try {
            return VsResponseUtil.ok("Business statistic success!", userService.getBusinessStatistic(request));

        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}

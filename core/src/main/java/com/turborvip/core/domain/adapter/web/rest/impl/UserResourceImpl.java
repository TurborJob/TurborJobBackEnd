package com.turborvip.core.domain.adapter.web.rest.impl;

import com.turborvip.core.domain.adapter.web.base.RestApiV1;
import com.turborvip.core.domain.adapter.web.base.VsResponseUtil;
import com.turborvip.core.domain.adapter.web.rest.UserResource;
import com.turborvip.core.domain.http.request.UpdateProfileRequest;
import com.turborvip.core.service.UserService;
import com.turborvip.core.model.entity.User;
import com.turborvip.core.service.impl.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@RestApiV1
@RequiredArgsConstructor
@Component("UserResourceImpl")
public class UserResourceImpl implements UserResource {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

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

    @Override
    public ResponseEntity<?> getAdminStatistic() {
        try {
            return VsResponseUtil.ok("Business statistic success!", userService.getAdminStatistic());

        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getAccountByAdmin(Map<String, Object> requestBody) {
        try {
            int page = (int) requestBody.get("page");
            int size = (int) requestBody.get("size");
            return VsResponseUtil.ok("Get account by admin success!", userService.getAccountByAdmin(page, size));

        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> adminUpdateStatusUser(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            String status = (String) requestBody.get("status");
            int idUser = (int) requestBody.get("idUser");
            User user = authService.getUserByHeader(request);

            userService.adminUpdateStatusUser(user, status, idUser);
            return VsResponseUtil.ok("Update status by admin success!");

        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> extendRoleBusiness(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            int numDayExtend = (int) requestBody.get("numDayExtend");
            int limitJobInDay = (int) requestBody.get("limitJobInDay");
            int limitWorkerInDay = (int) requestBody.get("limitWorkerInDay");
            User user = authService.getUserByHeader(request);
            userService.extendRoleBusiness(user, numDayExtend, limitJobInDay,limitWorkerInDay);
            return VsResponseUtil.ok("Extend success!");
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}

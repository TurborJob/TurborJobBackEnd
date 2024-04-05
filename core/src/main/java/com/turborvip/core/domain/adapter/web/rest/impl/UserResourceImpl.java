package com.turborvip.core.domain.adapter.web.rest.impl;

import com.turborvip.core.domain.adapter.web.base.RestApiV1;
import com.turborvip.core.domain.adapter.web.base.VsResponseUtil;
import com.turborvip.core.domain.adapter.web.rest.UserResource;
import com.turborvip.core.service.UserService;
import com.turborvip.core.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RestApiV1
@RequiredArgsConstructor
@Component("UserResourceImpl")
public class UserResourceImpl implements UserResource {
    private UserService userService;

    @Override
    public ResponseEntity<?> create(User user, HttpServletRequest request) {
        try{
            return VsResponseUtil.ok(null, userService.create(user, request), null);
        }catch (Exception e){
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

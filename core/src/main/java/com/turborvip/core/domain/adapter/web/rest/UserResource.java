package com.turborvip.core.domain.adapter.web.rest;

import com.turborvip.core.domain.http.request.ChangePassRequest;
import com.turborvip.core.domain.http.request.UpdateProfileRequest;
import com.turborvip.core.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Validated
public interface UserResource {
    @PostMapping("/no-auth/create-user")
    ResponseEntity<?> create(@RequestBody User user, HttpServletRequest request);

    @PostMapping("user/get-role-name")
    ResponseEntity<?> getRoleName(HttpServletRequest request);

    @PostMapping("auth/update-profile")
    ResponseEntity<?> updateProfile(HttpServletRequest request , @RequestBody UpdateProfileRequest updateProfileRequest);

    @PostMapping("user-only/update-business")
    ResponseEntity<?> updateProfile(HttpServletRequest request);

}

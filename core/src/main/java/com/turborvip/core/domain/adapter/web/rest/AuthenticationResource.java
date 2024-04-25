package com.turborvip.core.domain.adapter.web.rest;

import com.turborvip.core.domain.adapter.web.base.RestData;
import com.turborvip.core.domain.http.request.AuthRequest;
import com.turborvip.core.domain.http.request.ChangePassRequest;
import com.turborvip.core.model.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface AuthenticationResource {
    @PostMapping("/login")
    ResponseEntity<RestData<?>> login(@RequestBody AuthRequest authRequest, HttpServletRequest request) throws NoSuchAlgorithmException;

    @PostMapping("/logout")
    ResponseEntity<RestData<?>> logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws IOException;

    @PostMapping("/user/test")
    ResponseEntity<String> testAuth();

    @PostMapping("/auth/refresh-token")
    ResponseEntity<RestData<?>> refreshToken(HttpServletRequest request);

    @PostMapping("/all/register")
    ResponseEntity<RestData<?>> registerUser(@RequestBody UserDTO userDTO,HttpServletRequest request);

    @PostMapping("auth/change-pass")
    ResponseEntity<?> changePass(@RequestBody ChangePassRequest changePassRequest, HttpServletRequest request);
}

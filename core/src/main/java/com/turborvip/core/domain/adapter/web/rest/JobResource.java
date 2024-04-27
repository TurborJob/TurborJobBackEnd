package com.turborvip.core.domain.adapter.web.rest;

import com.turborvip.core.domain.adapter.web.base.RestData;
import com.turborvip.core.model.dto.JobDTO;
import com.turborvip.core.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Validated
public interface JobResource {
    @PostMapping("/business/create-job")
    ResponseEntity<?> createJob(HttpServletRequest request ,@RequestBody JobDTO jobDTO);

    @PostMapping("/business/get-job")
    ResponseEntity<RestData<?>> getJobByUser(HttpServletRequest request , @RequestBody Map<String, Object> requestBody);

    @PostMapping("/business/find-normal-job")
    ResponseEntity<RestData<?>> findNormalJob(HttpServletRequest request , @RequestBody Map<String, Object> requestBody);

    @PostMapping("/user-only/get-normal-job")
    ResponseEntity<RestData<?>> getNormalJobInsideUser(HttpServletRequest request , @RequestBody Map<String, Object> requestBody);
}

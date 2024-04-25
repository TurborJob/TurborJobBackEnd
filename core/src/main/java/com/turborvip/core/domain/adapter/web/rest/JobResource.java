package com.turborvip.core.domain.adapter.web.rest;

import com.turborvip.core.model.dto.JobDTO;
import com.turborvip.core.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
public interface JobResource {
    @PostMapping("/business/create-job")
    ResponseEntity<?> createJob(HttpServletRequest request ,@RequestBody JobDTO jobDTO);
}

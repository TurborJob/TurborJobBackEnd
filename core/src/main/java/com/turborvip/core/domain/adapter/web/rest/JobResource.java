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

    @PostMapping("/user-only/apply-normal-job")
    ResponseEntity<RestData<?>> applyNormalJob(HttpServletRequest request, @RequestBody Map<String, Object> requestBody);

    @PostMapping("/business/approve-normal-job")
    ResponseEntity<RestData<?>> approveNormalJob(HttpServletRequest request, @RequestBody Map<String, Object> requestBody);

    @PostMapping("/business/reject-normal-job")
    ResponseEntity<RestData<?>> rejectNormalJob(HttpServletRequest request, @RequestBody Map<String, Object> requestBody);

    @PostMapping("/user-only/get-request-normal-job")
    ResponseEntity<RestData<?>> getJobRequestNormalJobInsideWorker(HttpServletRequest request, @RequestBody Map<String, Object> requestBody);

    @PostMapping("/business/get-request-normal-job")
    ResponseEntity<RestData<?>> getJobRequestNormalJobInsideBusiness(HttpServletRequest request, @RequestBody Map<String, Object> requestBody);

    @PostMapping("/business/update-job-done")
    ResponseEntity<RestData<?>> updateDoneJobInBusinessSide(HttpServletRequest request,  @RequestBody Map<String, Object> requestBody);
}

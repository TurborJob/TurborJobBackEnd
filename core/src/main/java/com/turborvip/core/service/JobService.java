package com.turborvip.core.service;

import com.turborvip.core.domain.http.response.JobsResponse;
import com.turborvip.core.model.dto.JobDTO;
import com.turborvip.core.model.entity.Job;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {
    void createJob(HttpServletRequest request, JobDTO jobDTO) throws Exception;

    JobsResponse getJobsByUser(HttpServletRequest request, int page , int size) throws Exception;

    void findNormalJob(HttpServletRequest request, long jobId) throws Exception;

    JobsResponse getNormalJobInsideUser(HttpServletRequest request, Pageable pageable) throws Exception;
}

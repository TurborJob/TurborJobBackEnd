package com.turborvip.core.service;

import com.turborvip.core.model.dto.JobDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface JobService {
    void createJob(HttpServletRequest request, JobDTO jobDTO) throws Exception;
}

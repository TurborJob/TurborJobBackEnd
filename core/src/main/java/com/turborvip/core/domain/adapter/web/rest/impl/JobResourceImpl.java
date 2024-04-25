package com.turborvip.core.domain.adapter.web.rest.impl;

import com.turborvip.core.domain.adapter.web.base.RestApiV1;
import com.turborvip.core.domain.adapter.web.base.VsResponseUtil;
import com.turborvip.core.domain.adapter.web.rest.JobResource;
import com.turborvip.core.model.dto.JobDTO;
import com.turborvip.core.service.JobService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestApiV1
public class JobResourceImpl implements JobResource {

    @Autowired
    private JobService jobService;

    @Override
    public ResponseEntity<?> createJob(HttpServletRequest request , JobDTO jobDTO) {
        try{
            System.out.println(jobDTO.toString());
            jobService.createJob(request, jobDTO);
            return VsResponseUtil.ok("Create job successfully!");
        }catch (Exception e){
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

package com.turborvip.core.domain.adapter.web.rest.impl;

import com.turborvip.core.domain.adapter.web.base.RestApiV1;
import com.turborvip.core.domain.adapter.web.base.RestData;
import com.turborvip.core.domain.adapter.web.base.VsResponseUtil;
import com.turborvip.core.domain.adapter.web.rest.JobResource;
import com.turborvip.core.model.dto.JobDTO;
import com.turborvip.core.service.JobService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestApiV1
public class JobResourceImpl implements JobResource {

    @Autowired
    private JobService jobService;

    @Override
    public ResponseEntity<?> createJob(HttpServletRequest request , JobDTO jobDTO) {
        try{
            jobService.createJob(request, jobDTO);
            return VsResponseUtil.ok("Create job successfully!");
        }catch (Exception e){
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> getJobByUser(HttpServletRequest request, Map<String, Object> requestBody) {
        try{
            int page = (int) requestBody.get("page");
            int size = (int) requestBody.get("size");
            return VsResponseUtil.ok("Get job successfully!",jobService.getJobsByUser(request,page,size));
        }catch (Exception e){
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> findNormalJob(HttpServletRequest request, Map<String, Object> requestBody) {
        try{
            long jobId = (int) requestBody.get("jobId");
            jobService.findNormalJob(request,jobId);
            return VsResponseUtil.ok("Find successfully!");
        }catch (Exception e){
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> getNormalJobInsideUser(HttpServletRequest request, Map<String, Object> requestBody) {
        try{
            int page = (int) requestBody.get("page");
            int size = (int) requestBody.get("size");
            String createAt = (String) requestBody.get("createAt");

            Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

            if(createAt != null){
                sort = Sort.by(createAt, "createdAt");
            }

            Pageable pageable = PageRequest.of(page, size, sort);
            return VsResponseUtil.ok("Get job successfully!",jobService.getNormalJobInsideUser(request,pageable));
        }catch (Exception e){
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

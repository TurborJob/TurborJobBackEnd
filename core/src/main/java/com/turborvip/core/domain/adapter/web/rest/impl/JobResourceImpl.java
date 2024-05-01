package com.turborvip.core.domain.adapter.web.rest.impl;

import com.turborvip.core.domain.adapter.web.base.RestApiV1;
import com.turborvip.core.domain.adapter.web.base.RestData;
import com.turborvip.core.domain.adapter.web.base.VsResponseUtil;
import com.turborvip.core.domain.adapter.web.rest.JobResource;
import com.turborvip.core.model.dto.JobDTO;
import com.turborvip.core.service.JobService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestApiV1
public class JobResourceImpl implements JobResource {

    @Autowired
    private JobService jobService;

    @Override
    public ResponseEntity<?> createJob(HttpServletRequest request, JobDTO jobDTO) {
        try {
            jobService.createJob(request, jobDTO);
            return VsResponseUtil.ok("Create job successfully!");
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> getJobByUser(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            int page = (int) requestBody.get("page");
            int size = (int) requestBody.get("size");
            return VsResponseUtil.ok("Get job successfully!", jobService.getJobsByUser(request, page, size));
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> findNormalJob(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            long jobId = (int) requestBody.get("jobId");
            jobService.findNormalJob(request, jobId);
            return VsResponseUtil.ok("Find successfully!");
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> getNormalJobInsideUser(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            int page = (int) requestBody.get("page");
            int size = (int) requestBody.get("size");
            double lng = (double) requestBody.get("long");
            double lat = (double) requestBody.get("lat");

            return VsResponseUtil.ok("Get job successfully!", jobService.getNormalJobInsideUser(request, page, size, lat, lng));
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> applyNormalJob(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            long jobId = (int) requestBody.get("jobId");
            String description = (String) requestBody.get("description");
            jobService.workerApplyJob(request, jobId, description);
            return VsResponseUtil.ok("Apply job successfully!");
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> approveNormalJob(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            long jobId = (int) requestBody.get("jobId");
            long userReqId = (int) requestBody.get("userReqId");
            String description = (String) requestBody.get("description");
            jobService.approveRequestJob(request, jobId, userReqId, description);
            return VsResponseUtil.ok("Approve request successfully!");
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> rejectNormalJob(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            long jobId = (int) requestBody.get("jobId");
            long userReqId = (int) requestBody.get("userReqId");
            String description = (String) requestBody.get("description");
            jobService.rejectRequestJob(request, jobId, userReqId, description);
            return VsResponseUtil.ok("Reject request successfully!");
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> getJobRequestNormalJobInsideWorker(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            int page = (int) requestBody.get("page");
            int size = (int) requestBody.get("size");
            return VsResponseUtil.ok("Get request successfully!", jobService.getJobRequestApplyInsideWorker(request, page, size));
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> getJobRequestNormalJobInsideBusiness(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            int page = (int) requestBody.get("page");
            int size = (int) requestBody.get("size");
            long jobId = (int) requestBody.get("jobId");
            return VsResponseUtil.ok("Get request successfully!", jobService.getJobRequestInsideBusiness(request, page, size, jobId));
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<RestData<?>> updateDoneJobInBusinessSide(HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            long jobId = (int) requestBody.get("jobId");
            jobService.updateJobDoneBusinessSide(request, jobId);
            return VsResponseUtil.ok("Update successfully!");
        } catch (Exception e) {
            return VsResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}

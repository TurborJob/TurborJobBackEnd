package com.turborvip.core.service;

import com.turborvip.core.domain.http.response.JobResponse;
import com.turborvip.core.domain.http.response.JobsResponse;
import com.turborvip.core.domain.http.response.ProfilesResponse;
import com.turborvip.core.model.dto.JobDTO;
import com.turborvip.core.model.entity.Job;
import com.turborvip.core.model.entity.JobUser;
import com.turborvip.core.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {
    void createJob(HttpServletRequest request, JobDTO jobDTO) throws Exception;

    JobsResponse getJobsByUser(HttpServletRequest request, int page , int size) throws Exception;

    void findNormalJob(HttpServletRequest request, long jobId) throws Exception;

    void findNormalJobRunTime(User user, long jobId) throws Exception;

    JobsResponse getNormalJobInsideUser(HttpServletRequest request, int page , int size, double lng, double lat) throws Exception;

    JobsResponse getRunTimeJob(User user, int page, int size, double lng, double lat) throws Exception;

    void workerApplyJob(HttpServletRequest request, long jobId, String description) throws Exception;

    void approveRequestJob(HttpServletRequest request, long jobId, long userReqId, String description) throws Exception;

    void rejectRequestJob(HttpServletRequest request, long jobId, long userReqId, String description) throws Exception;

    JobsResponse getJobRequestApplyInsideWorker(HttpServletRequest request, int page, int size) throws Exception;

    ProfilesResponse getJobRequestInsideBusiness(HttpServletRequest request, int page, int size, long jobId) throws Exception;

    void updateJobDoneBusinessSide(HttpServletRequest request, long jobId) throws Exception;
}

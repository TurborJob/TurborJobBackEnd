package com.turborvip.core.service.impl;

import com.turborvip.core.domain.http.response.JobResponse;
import com.turborvip.core.domain.http.response.JobsResponse;
import com.turborvip.core.domain.repositories.JobRepository;
import com.turborvip.core.domain.repositories.JobUserRepository;
import com.turborvip.core.model.dto.JobDTO;
import com.turborvip.core.model.entity.Job;
import com.turborvip.core.model.entity.JobUser;
import com.turborvip.core.model.entity.User;
import com.turborvip.core.service.JobService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class JobServiceImpl implements JobService {
    private final JobUserRepository jobUserRepository;
    private final JobRepository jobRepository;
    @Autowired
    private final AuthService authService;

    @Override
    public void createJob(HttpServletRequest request, JobDTO jobDTO) throws Exception {
        User user = authService.getUserByHeader(request);

        Job job = new Job(jobDTO.getName(), jobDTO.getAddress(), jobDTO.getImages(), jobDTO.getDescription(), jobDTO.getQuantityWorker(),
                new Timestamp(jobDTO.getStartDate().getTime()), new Timestamp(jobDTO.getDueDate().getTime()), jobDTO.isVehicle(),
                jobDTO.getGender(), jobDTO.getLat(), jobDTO.getLng(), jobDTO.getSalary());
        job.setCreateBy(user);
        job.setUpdateBy(user);

        jobRepository.save(job);
    }

    @Override
    public JobsResponse getJobsByUser(HttpServletRequest request, int page, int size) throws Exception {
        User user = authService.getUserByHeader(request);
        Pageable pageable = PageRequest.of(page, size);
        long total = jobRepository.countByCreateBy(user);
        List<JobResponse> jobs = jobRepository.findByCreateByOrderByCreateAtDesc(user, pageable);
        return new JobsResponse(jobs, total);
    }

    @Override
    public JobResponse findNormalJob(HttpServletRequest request, long jobId) throws Exception {
        User user = authService.getUserByHeader(request);

        JobResponse job = jobRepository.findByCreateByAndId(user, jobId).orElse(null);
        if (job == null) {
            throw new Exception("Don't have job!");
        }
        return job;
    }

    @Override
    public JobsResponse getNormalJobInsideUser(HttpServletRequest request, int page, int size, double lng, double lat) throws Exception {
        User user = authService.getUserByHeader(request);
        Pageable pageable = PageRequest.of(page, size);
        List<String> genderQuery = new ArrayList<>();
        genderQuery.add("all");
        genderQuery.add(user.getGender());
        long total = jobRepository.countByCreateByNotAndStatusAndGenderIn(user,"processing", genderQuery);
        List<Job> jobs = jobRepository.findNearestJobsWithoutUser(user, genderQuery, "processing", lng, lat, pageable);
        return new JobsResponse(jobs, total);
    }
}

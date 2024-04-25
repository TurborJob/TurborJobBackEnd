package com.turborvip.core.service.impl;

import com.turborvip.core.domain.repositories.JobRepository;
import com.turborvip.core.model.dto.JobDTO;
import com.turborvip.core.model.entity.Job;
import com.turborvip.core.model.entity.User;
import com.turborvip.core.service.JobService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    @Autowired
    private final AuthService authService;

    @Override
    public void createJob(HttpServletRequest request, JobDTO jobDTO) throws Exception {
        User user = authService.getUserByHeader(request);

        Job job = new Job(jobDTO.getName(), jobDTO.getAddress(), jobDTO.getImages(), jobDTO.getDescription(), jobDTO.getQuantityWorker(),
                new Timestamp(jobDTO.getStartDate().getTime()), new Timestamp(jobDTO.getDueDate().getTime()), jobDTO.getIpAddress(), jobDTO.isVehicle(), jobDTO.getGender(), jobDTO.getLat(), jobDTO.getLng());
        job.setCreateBy(user);
        job.setUpdateBy(user);

        jobRepository.save(job);
    }
}

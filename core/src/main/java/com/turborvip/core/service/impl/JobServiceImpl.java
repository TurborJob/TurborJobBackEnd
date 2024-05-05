package com.turborvip.core.service.impl;

import com.turborvip.core.domain.http.response.JobResponse;
import com.turborvip.core.domain.http.response.JobsResponse;
import com.turborvip.core.domain.http.response.ProfilesResponse;
import com.turborvip.core.domain.repositories.JobRepository;
import com.turborvip.core.domain.repositories.JobUserRepository;
import com.turborvip.core.domain.repositories.RateHistoryRepository;
import com.turborvip.core.model.dto.BusinessDTO;
import com.turborvip.core.model.dto.JobDTO;
import com.turborvip.core.model.dto.ProfileRequest;
import com.turborvip.core.model.entity.Job;
import com.turborvip.core.model.entity.JobUser;
import com.turborvip.core.model.entity.RateHistory;
import com.turborvip.core.model.entity.User;
import com.turborvip.core.model.entity.compositeKey.JobUserKey;
import com.turborvip.core.model.entity.compositeKey.RateHistoryKey;
import com.turborvip.core.service.JobService;
import com.turborvip.core.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class JobServiceImpl implements JobService {
    private final RateHistoryRepository rateHistoryRepository;
    private final JobUserRepository jobUserRepository;
    private final JobRepository jobRepository;

    @Autowired
    private final AuthService authService;

    @Autowired
    private final NotificationService notificationService;

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
    public void findNormalJob(HttpServletRequest request, long jobId) throws Exception {
        User user = authService.getUserByHeader(request);

        Job job = jobRepository.findByCreateByAndId(user, jobId).orElse(null);
        if (job == null) {
            throw new Exception("Don't have job!");
        }
        job.setStatus("processing");
        jobRepository.save(job);
    }

    @Override
    public void findNormalJobRunTime(User user, long jobId) throws Exception {
        Job job = jobRepository.findByCreateByAndId(user, jobId).orElse(null);
        if (job == null) {
            throw new Exception("Don't have job!");
        }
        job.setStatus("processing");
        jobRepository.save(job);
    }

    @Override
    public JobsResponse getNormalJobInsideUser(HttpServletRequest request, int page, int size, double lng, double lat) throws Exception {
        User user = authService.getUserByHeader(request);
        Pageable pageable = PageRequest.of(page, size);
        List<String> genderQuery = new ArrayList<>();
        genderQuery.add("all");
        genderQuery.add(user.getGender());
        List<JobUser> jobUsers = jobUserRepository.findByUserId(user);
        List<Long> jobsIdApplied = new ArrayList<>();
        jobUsers.forEach(i -> jobsIdApplied.add(i.getJobId().getId()));

        long total = 0;
        List<Job> jobs = new ArrayList<>();

        if (jobsIdApplied.isEmpty()) {
            total = jobRepository.countByCreateByNotAndStatusAndGenderIn(user, "processing", genderQuery);
            jobs = jobRepository.findNearestJobsWithoutUser(user, genderQuery, "processing", lng, lat, pageable);
        } else {
            total = jobRepository.countByCreateByNotAndStatusAndGenderInAndIdNotIn(user, "processing", genderQuery, jobsIdApplied);
            jobs = jobRepository.findNearestJobsWithoutUserWithNotIn(jobsIdApplied, user, genderQuery, "processing", lng, lat, pageable);
        }

        return new JobsResponse(jobs, total);
    }

    @Override
    public JobsResponse getRunTimeJob(User user, int page, int size, double lng, double lat) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        List<String> genderQuery = new ArrayList<>();
        genderQuery.add("all");
        genderQuery.add(user.getGender());
        List<JobUser> jobUsers = jobUserRepository.findByUserId(user);
        List<Long> jobsIdApplied = new ArrayList<>();
        jobUsers.forEach(i -> jobsIdApplied.add(i.getJobId().getId()));

        long total = 0;
        List<Job> jobs = new ArrayList<>();

        if (jobsIdApplied.isEmpty()) {
            total = jobRepository.countByCreateByNotAndStatusAndGenderIn(user, "processing", genderQuery);
            jobs = jobRepository.findNearestJobsWithoutUser(user, genderQuery, "processing", lng, lat, pageable);
        } else {
            total = jobRepository.countByCreateByNotAndStatusAndGenderInAndIdNotIn(user, "processing", genderQuery, jobsIdApplied);
            jobs = jobRepository.findNearestJobsWithoutUserWithNotIn(jobsIdApplied, user, genderQuery, "processing", lng, lat, pageable);
        }

        return new JobsResponse(jobs, total);
    }

    @Override
    public void workerApplyJob(HttpServletRequest request, long jobId, String description) throws Exception {
        User user = authService.getUserByHeader(request);
        Job job = jobRepository.findById(jobId).orElse(null);
        if (job == null) {
            throw new Exception("Don't have job!");
        }
        if (job.getQuantityWorkerCurrent() >= job.getQuantityWorkerTotal()) {
            throw new Exception("exceeded the number of workers");
        }

        boolean checkTime = jobUserRepository.existsByUserIdAndStatusAndJobId_StartDateBetweenOrJobId_DueDateBetween(user,"approve"
                ,job.getStartDate(),job.getDueDate(),job.getStartDate(),job.getDueDate());

        if(checkTime){
            throw new Exception("You do not satisfy the time requirement (due to other work during the corresponding time)");
        }

        jobUserRepository.save(new JobUser(new JobUserKey(job.getId(), user.getId()), user, job, "pending", description));
        String note = "From "+ user.getFullName() + " (" + job.getName() + ") : " + description;
        notificationService.createNotificationApplyReqForBusiness(user, job.getCreateBy(), note, "push");
        jobRepository.save(job);
    }

    @Override
    public void approveRequestJob(HttpServletRequest request, long jobId, long userReqId, String description) throws Exception {
        User user = authService.getUserByHeader(request);
        JobUser jobUser = jobUserRepository.findByUserId_IdAndJobId_Id(userReqId, jobId).orElse(null);
        if (jobUser == null) {
            throw new Exception("Don't have request job!");
        }
        Job job = jobUser.getJobId();
        if (job.getQuantityWorkerCurrent() >= job.getQuantityWorkerTotal()) {
            throw new Exception("Job is success not approve request!");
        }

        jobUser.setStatus("approve");

        job.setQuantityWorkerCurrent(job.getQuantityWorkerCurrent() + 1);
        String note = "From "+ user.getFullName() + " (" + job.getName() + ") : " + description;
        notificationService.createNotificationApproveReqForWorker(job, user, jobUser.getUserId(), note, "push");
        if (job.getQuantityWorkerCurrent() == job.getQuantityWorkerTotal()) {
            job.setStatus("success");
        }
        jobUserRepository.save(jobUser);
        jobRepository.save(job);
    }

    @Override
    public void rejectRequestJob(HttpServletRequest request, long jobId, long userReqId, String description) throws Exception {
        User user = authService.getUserByHeader(request);
        JobUser jobUser = jobUserRepository.findByUserId_IdAndJobId_Id(userReqId, jobId).orElse(null);
        if (jobUser == null) {
            throw new Exception("Don't have request job!");
        }
        jobUser.setStatus("reject");
        jobUserRepository.save(jobUser);
        String note = "From "+ user.getFullName() + " (" + jobUser.getJobId().getName() + ") : " + description;
        notificationService.createNotificationRejectReqForWorker(jobUser.getJobId(), user, jobUser.getUserId(), note, "push");
    }

    @Override
    public JobsResponse getJobRequestApplyInsideWorker(HttpServletRequest request, int page, int size) throws Exception {
        User user = authService.getUserByHeader(request);
        Sort sort = Sort.by(Sort.Direction.DESC, "createAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<JobUser> jobUsers = jobUserRepository.findJobUserByUserId(user, pageable);
        long total = jobUserRepository.countByUserId(user);
        return new JobsResponse(jobUsers, total);
    }

    @Override
    public ProfilesResponse getJobRequestInsideBusiness(HttpServletRequest request, int page, int size, long jobId) throws Exception {
        User user = authService.getUserByHeader(request);
        Sort sort = Sort.by(Sort.Direction.DESC, "createAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        Job job = jobRepository.findById(jobId).orElse(null);
        if (job == null) {
            throw new Exception("Job invalid!");
        }
        List<JobUser> jobUsers = jobUserRepository.findByJobIdAndStatus(job, "pending", pageable);
        List<ProfileRequest> users = new ArrayList<>();
        jobUsers.forEach(i -> users.add(i.getUserId().getProfileAndNote(i.getDescription())));
        long total = jobUserRepository.countByUserId(user);
        return new ProfilesResponse(users, total);
    }

    @Override
    public void updateJobDoneBusinessSide(HttpServletRequest request, long jobId) throws Exception {
        User ownerJob = authService.getUserByHeader(request);
        Job job = jobRepository.findByIdAndCreateBy(jobId, ownerJob).orElse(null);
        if (job == null) {
            throw new Exception("Job invalid!");
        }
        job.setStatus("done");
        jobRepository.save(job);

        List<JobUser> jobUsers = jobUserRepository.findByJobIdAndStatus(job, "approve", null);
        List<User> users = new ArrayList<>();
        jobUsers.forEach(i -> users.add(i.getUserId()));

        List<RateHistory> rateHistories = new ArrayList<>();

        for (User user : users) {
            /* Todo rate from worker to Business*/
            RateHistoryKey rateHistoryKeyWorkerBusiness = new RateHistoryKey(user.getId(), ownerJob.getId());
            RateHistory rateHistoryWorkerBusiness = new RateHistory(rateHistoryKeyWorkerBusiness, user, ownerJob, job.getName(), null, null);
            rateHistories.add(rateHistoryWorkerBusiness);
            //notificationService.createNotificationRejectReqForWorker(job, user, ownerJob, "Please rating for me!", "push");

            /* Todo rate from Business to Worker*/
            RateHistoryKey rateHistoryKeyBusinessWorker = new RateHistoryKey(ownerJob.getId(), user.getId());
            RateHistory rateHistoryBusinessWorker = new RateHistory(rateHistoryKeyBusinessWorker, ownerJob, user, job.getName(), null, null);
            rateHistories.add(rateHistoryBusinessWorker);
        }
        rateHistoryRepository.saveAll(rateHistories);
    }

    @Override
    public boolean checkJobIsSuccess(long jobId) throws Exception {
        Job job = jobRepository.findById(jobId).orElse(null);
        if(job == null){
            throw new Exception("Job invalid!");
        }
        return job.getStatus().equals("success");
    }

    @Override
    public void updateJobExpireStartDate() throws Exception {
        Date now = new Date();
        ArrayList<String> statuses = new ArrayList<>();
        // inactive, processing, success, done, fail.
        statuses.add("inactive");
        statuses.add("processing");
        List<Job> jobs = jobRepository.findByStartDateGreaterThanAndStatusIn(new Timestamp(now.getTime()), statuses);
        jobs.forEach(i->i.setStatus("fail"));
    }

    @Override
    public long findUserIdByJobId(long jobId) throws Exception {
        Job job = jobRepository.findById(jobId).orElse(null);
        if (job == null){
            throw new Exception("Job invalid");
        }
        return job.getCreateBy().getId();
    }

}

package com.turborvip.core.domain.repositories;

import com.turborvip.core.model.entity.Job;
import com.turborvip.core.model.entity.JobUser;
import com.turborvip.core.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobUserRepository extends JpaRepository<JobUser, Long> {

    List<JobUser> findByUserId(User userId);

    Optional<JobUser> findByUserId_IdAndJobId_Id(Long id, Long id1);

    List<JobUser> findJobUserByUserId(User userId, Pageable pageable);

    long countByUserId(User userId);

    List<JobUser> findByJobIdAndStatus(Job jobId, String status, Pageable pageable);


}

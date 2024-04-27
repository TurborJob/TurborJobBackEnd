package com.turborvip.core.domain.repositories;

import com.turborvip.core.model.entity.JobUser;
import com.turborvip.core.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobUserRepository extends JpaRepository<JobUser, Long> {

    List<JobUser> findByUserId(User userId);
}

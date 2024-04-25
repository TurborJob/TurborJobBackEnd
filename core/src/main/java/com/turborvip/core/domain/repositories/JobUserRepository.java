package com.turborvip.core.domain.repositories;

import com.turborvip.core.model.entity.JobUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobUserRepository extends JpaRepository<JobUser, Long> {

}

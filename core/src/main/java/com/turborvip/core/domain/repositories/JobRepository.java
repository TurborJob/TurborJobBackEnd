package com.turborvip.core.domain.repositories;

import com.turborvip.core.model.entity.Job;
import com.turborvip.core.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByCreateBy(User createBy, Pageable pageable);

    Optional<Job> findByCreateByAndId(User createBy, long id);

}

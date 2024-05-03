package com.turborvip.core.domain.repositories;

import com.turborvip.core.model.entity.JobsRunTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRunTimeRepository extends CrudRepository<JobsRunTime, Long> {

    void deleteById(Long s);

    List<JobsRunTime> findByRecipientId(String recipientId);

}


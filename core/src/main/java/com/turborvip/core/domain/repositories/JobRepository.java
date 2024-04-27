package com.turborvip.core.domain.repositories;

import com.turborvip.core.domain.http.response.JobResponse;
import com.turborvip.core.model.entity.Job;
import com.turborvip.core.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<JobResponse> findByCreateByOrderByCreateAtDesc(User createBy, Pageable pageable);

    long countByCreateBy(User createBy);

    Optional<JobResponse> findByCreateByAndId(User createBy, long id);

    List<JobResponse> findByCreateByNot(User createBy, Pageable pageable);

    long countByCreateByNot(User createBy);

    @Query(value = "select j from job.jobs j " +
            "where j.createBy.id <> :createBy and j.gender in :genders and j.status = :status " +
            "order by ST_Distance(j.location, ST_MakePoint(:longitude, :latitude))",
            countQuery = "select count(id) from Job j j.createBy.id <> :createBy and j.gender in :genders and j.status = :status",
            nativeQuery = true
    )
    List<JobResponse> findNearestJobsWithoutUser(@Param("createBy") long userId, @Param("genders") Collection<String> genders,
                                                 @Param("status") String status, Pageable pageable, @Param("longitude") double lng, @Param("latitude") double lat);





}

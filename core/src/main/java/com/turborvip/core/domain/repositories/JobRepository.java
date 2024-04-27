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

    Optional<Job> findByCreateByAndId(User createBy, long id);

    List<JobResponse> findByCreateByNot(User createBy, Pageable pageable);

    long countByCreateByNot(User createBy);

    @Query(value = "select j from Job j " +
            "where j.createBy <> :createBy and j.gender in :genders and j.status = :status " +
            "order by ST_Distance(j.coordinates, ST_SetSRID(ST_MakePoint(:longitude, :latitude),4326))",
            countQuery = "select count(id) from Job j j.createBy <> :createBy and j.gender in :genders and j.status = :status"
    )
    List<Job> findNearestJobsWithoutUser(@Param("createBy") User user, @Param("genders") Collection<String> genders,
                                                 @Param("status") String status, @Param("longitude") double lng, @Param("latitude") double lat, Pageable pageable);

    long countByCreateByNotAndStatusAndGenderIn(User createBy, String status, Collection<String> genders);


    @Query(value = "select j from Job j " +
            "where j.id not in :jobsApplied and j.createBy <> :createBy and j.gender in :genders and j.status = :status " +
            "order by ST_Distance(j.coordinates, ST_SetSRID(ST_MakePoint(:longitude, :latitude),4326))",
            countQuery = "select count(id) from Job j j.createBy <> :createBy and j.gender in :genders and j.status = :status"
    )
    List<Job> findNearestJobsWithoutUserWithNotIn(@Param("jobsApplied")Collection<Long> ids, @Param("createBy") User user, @Param("genders") Collection<String> genders,
                                                  @Param("status") String status, @Param("longitude") double lng, @Param("latitude") double lat, Pageable pageable);

    long countByCreateByNotAndStatusAndGenderInAndIdNotIn(User createBy, String status, Collection<String> genders, Collection<Long> ids);

    List<Job> findByIdNotIn(Collection<Long> ids);
}

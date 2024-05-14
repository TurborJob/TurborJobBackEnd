package com.turborvip.core.domain.repositories;

import com.turborvip.core.domain.http.response.JobResponse;
import com.turborvip.core.model.entity.Job;
import com.turborvip.core.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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

    Optional<Job> findByIdAndCreateBy(Long id, User createBy);

    List<Job> findByStartDateGreaterThanAndStatusIn(Timestamp startDate, Collection<String> statuses);

    long countByCreateByAndStatus(User createBy, String status);

    @Query(value = "SELECT j FROM Job j " +
            "WHERE j.createBy <> :createBy " +
            "AND j.gender IN :genders " +
            "AND j.status = :status " +
            "AND j.isVehicle = :isVehicle " +
            "AND j.salary BETWEEN :salaryFrom AND :salaryTo " +
            "ORDER BY ST_Distance(j.coordinates, ST_SetSRID(ST_MakePoint(:longitude, :latitude),4326))",
            countQuery = "SELECT COUNT(j.id) FROM Job j " +
                    "WHERE j.createBy <> :createBy " +
                    "AND j.gender IN :genders " +
                    "AND j.status = :status " +
                    "AND j.isVehicle = :isVehicle " +
                    "AND j.salary BETWEEN :salaryFrom AND :salaryTo"
    )
    List<Job> findNearestJobsWithoutUserAndFilter(@Param("createBy") User user, @Param("genders") Collection<String> genders,
                                         @Param("status") String status, @Param("longitude") double lng, @Param("latitude") double lat,
                                         @Param("isVehicle") boolean isVehicle, @Param("salaryFrom") double salaryFrom, @Param("salaryTo") double salaryTo, Pageable pageable);

    long countByCreateByNotAndStatusAndGenderInAndIsVehicleAndSalaryBetween(User createBy, String status, Collection<String> genders, boolean isVehicle, float salaryStart, float salaryEnd);


    @Query(value = "SELECT j FROM Job j " +
            "WHERE j.createBy <> :createBy " +
            "AND j.id not in :jobsApplied "+
            "AND j.gender IN :genders " +
            "AND j.status = :status " +
            "AND j.isVehicle = :isVehicle " +
            "AND j.salary BETWEEN :salaryFrom AND :salaryTo " +
            "ORDER BY ST_Distance(j.coordinates, ST_SetSRID(ST_MakePoint(:longitude, :latitude),4326))",
            countQuery = "SELECT COUNT(j.id) FROM Job j " +
                    "WHERE j.createBy <> :createBy " +
                    "AND j.gender IN :genders " +
                    "AND j.status = :status " +
                    "AND j.isVehicle = :isVehicle " +
                    "AND j.salary BETWEEN :salaryFrom AND :salaryTo"
    )
    List<Job> findNearestJobsWithoutUserWithNotInAndFilter(@Param("jobsApplied")Collection<Long> ids, @Param("createBy") User user, @Param("genders") Collection<String> genders,
                                                  @Param("status") String status, @Param("longitude") double lng, @Param("latitude") double lat,
                                                  @Param("isVehicle") boolean isVehicle, @Param("salaryFrom") double salaryFrom, @Param("salaryTo") double salaryTo, Pageable pageable);

    long countByCreateByNotAndStatusAndGenderInAndIdNotInAndIsVehicleAndSalaryBetween(User createBy, String status, Collection<String> genders, Collection<Long> ids, boolean isVehicle, float salaryStart, float salaryEnd);

}

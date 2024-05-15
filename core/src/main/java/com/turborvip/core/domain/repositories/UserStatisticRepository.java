package com.turborvip.core.domain.repositories;

import com.turborvip.core.model.entity.User;
import com.turborvip.core.model.entity.UserRole;
import com.turborvip.core.model.entity.UserStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserStatisticRepository extends JpaRepository<UserStatistic, Long> {

    Optional<UserStatistic> findByUser(User user);

    @Transactional
    @Modifying
    @Query("update UserStatistic u set u.jobSuccessInDay = ?1, u.userApproveInDay = ?2")
    void updateJobSuccessInDayAndUserApproveInDayBy(long jobSuccessInDay, long userApproveInDay);

    @Transactional
    @Modifying
    @Query("update UserStatistic u set u.jobSuccessInMonth = ?1, u.userApproveInMonth = ?2")
    void updateJobSuccessInMonthAndUserApproveInMonthBy(long jobSuccessInMonth, long userApproveInMonth);


}

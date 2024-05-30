package com.turborvip.core.service.impl;

import com.turborvip.core.domain.repositories.UserStatisticRepository;
import com.turborvip.core.model.entity.Job;
import com.turborvip.core.model.entity.User;
import com.turborvip.core.model.entity.UserStatistic;
import com.turborvip.core.service.UserStatisticService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserStatisticServiceImpl implements UserStatisticService {
    @Autowired
    private UserStatisticRepository userStatisticRepository;

    @Override
    public void updateUserStatistic(User user, Job job) {
        UserStatistic userStatistic = userStatisticRepository.findByUser(user).orElse(null);

        if (userStatistic == null) {
            UserStatistic newUserStatistic = new UserStatistic(user, job.getQuantityWorkerTotal(), 1, job.getQuantityWorkerTotal(),
                    1, job.getQuantityWorkerTotal(), 1);
            userStatisticRepository.save(newUserStatistic);
        } else {
            Date now = new Date();

            // Todo update in day
            userStatistic.setJobSuccessInDay(userStatistic.getJobSuccessInDay() + 1);
            userStatistic.setUserApproveInDay(userStatistic.getUserApproveInDay() + job.getQuantityWorkerTotal());

            // Todo update in month
            userStatistic.setJobSuccessInMonth(userStatistic.getJobSuccessInMonth() + 1);
            userStatistic.setUserApproveInMonth(userStatistic.getUserApproveInMonth() + job.getQuantityWorkerTotal());

            userStatistic.setJobSuccess(userStatistic.getJobSuccess() + 1);
            userStatistic.setUserApprove(userStatistic.getUserApprove() + job.getQuantityWorkerTotal());
            userStatistic.setUpdateAt(new Timestamp(now.getTime()));
            userStatisticRepository.save(userStatistic);
        }
    }

    @Override
    public  void updateUserStatisticEndOfDay(){
        userStatisticRepository.updateJobSuccessInDayAndUserApproveInDayBy(0,0);
    }

    @Override
    public  void updateUserStatisticEndOfMonth(){
        userStatisticRepository.updateJobSuccessInMonthAndUserApproveInMonthBy(0,0);
    }
}

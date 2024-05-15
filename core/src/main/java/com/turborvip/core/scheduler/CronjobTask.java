package com.turborvip.core.scheduler;

import com.turborvip.core.domain.repositories.UserStatisticRepository;
import com.turborvip.core.service.JobService;
import com.turborvip.core.service.UserStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronjobTask {

    @Autowired
    JobService jobService;

    @Autowired
    UserStatisticService userStatisticService;

    // TODO update job expire start date of a day
    /** @Scheduled(cron = "0 0 0 * * ?")
    public void resetViewOfDay() throws Exception {
        jobService.updateJobExpireStartDate();
        System.out.println("\n" + "Update Job Expire start date of day .......");
    }
     */

    // Todo update end of day
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetUserStatisticOfDay() throws Exception {
        userStatisticService.updateUserStatisticEndOfDay();
        System.out.println("\n" + "Update user statistic of day .......");
    }


    // Todo update end of month
    @Scheduled(cron = "0 0 0 1 * ?")
    public void resetUserStatisticOfMonth() {
        userStatisticService.updateUserStatisticEndOfMonth();
        System.out.println("\n" + "Update user statistic of month .......");
    }
}

package com.turborvip.core.scheduler;

import com.turborvip.core.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronjobTask {

    @Autowired
    JobService jobService;

    // TODO update job expire start date of a day
    /** @Scheduled(cron = "0 0 0 * * ?")
    public void resetViewOfDay() throws Exception {
        jobService.updateJobExpireStartDate();
        System.out.println("\n" + "Update Job Expire start date of day .......");
    }
     */
}

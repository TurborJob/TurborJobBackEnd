package com.turborvip.core.service;

import com.turborvip.core.model.entity.Job;
import com.turborvip.core.model.entity.User;

public interface UserStatisticService {
    void updateUserStatistic(User user, Job job);

    void updateUserStatisticEndOfDay();

    void updateUserStatisticEndOfMonth();
}

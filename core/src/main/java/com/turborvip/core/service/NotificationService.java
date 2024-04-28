package com.turborvip.core.service;

import com.turborvip.core.model.dto.JobDTO;
import com.turborvip.core.model.entity.Job;
import com.turborvip.core.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

public interface NotificationService {

    void createNotificationApplyReqForBusiness(User sender, User receiver, String contentSender, String channel) throws Exception;

    void createNotificationApproveReqForWorker(Job job, User sender, User receiver, String contentSender, String channel) throws Exception;

    void createNotificationRejectReqForWorker(Job job, User sender, User receiver, String contentSender, String channel) throws Exception;

}

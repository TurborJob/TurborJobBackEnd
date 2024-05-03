package com.turborvip.core.service;

import com.turborvip.core.model.dto.JobDTO;
import com.turborvip.core.model.entity.Job;
import com.turborvip.core.model.entity.Notification;
import com.turborvip.core.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.List;

public interface NotificationService {

    void createNotificationApplyReqForBusiness(User sender, User receiver, String contentSender, String channel) throws Exception;

    void createNotificationApproveReqForWorker(Job job, User sender, User receiver, String contentSender, String channel) throws Exception;

    void createNotificationRejectReqForWorker(Job job, User sender, User receiver, String contentSender, String channel) throws Exception;

    void createNotificationReqRating(Job job, User sender, User receiver, String contentSender, String channel) throws Exception;

    long getNumNotifyPushUnReadByUser(HttpServletRequest request) throws Exception;

    void markAllNotifyIsReadByUser(HttpServletRequest request) throws Exception;

    List<Notification> getNotifyByUser(HttpServletRequest request, int page, int size) throws Exception;
}

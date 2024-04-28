package com.turborvip.core.service.impl;

import com.turborvip.core.domain.repositories.NotificationRepository;
import com.turborvip.core.model.entity.Job;
import com.turborvip.core.model.entity.Notification;
import com.turborvip.core.model.entity.User;
import com.turborvip.core.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public void createNotificationApplyReqForBusiness(User sender, User receiver, String contentSender, String channel) throws Exception {
        Notification notification = new Notification("Apply Job Request", contentSender, channel , false, receiver);
        notification.setCreateBy(sender);
        notification.setUpdateBy(sender);
        notificationRepository.save(notification);
    }

    @Override
    public void createNotificationApproveReqForWorker(Job job, User sender, User receiver, String contentSender, String channel) throws Exception {
        Notification notification = new Notification("Approve Job: "+ job.getName(), contentSender, channel , false, receiver);
        notification.setCreateBy(sender);
        notification.setUpdateBy(sender);
        notificationRepository.save(notification);

    }

    @Override
    public void createNotificationRejectReqForWorker(Job job, User sender, User receiver, String contentSender, String channel) throws Exception {
        Notification notification = new Notification("Reject Job: "+ job.getName(), contentSender, channel , false, receiver);
        notification.setCreateBy(sender);
        notification.setUpdateBy(sender);
        notificationRepository.save(notification);
    }
}

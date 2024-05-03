package com.turborvip.core.service.impl;

import com.turborvip.core.domain.repositories.NotificationRepository;
import com.turborvip.core.model.entity.Job;
import com.turborvip.core.model.entity.Notification;
import com.turborvip.core.model.entity.User;
import com.turborvip.core.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    private final AuthService authService;

    @Override
    public void createNotificationApplyReqForBusiness(User sender, User receiver, String contentSender, String channel) throws Exception {
        Notification notification = new Notification("Apply Job Request", contentSender, channel , false, receiver.getId());
        notification.setCreateBy(sender);
        notification.setUpdateBy(sender);
        notificationRepository.save(notification);
    }

    @Override
    public void createNotificationApproveReqForWorker(Job job, User sender, User receiver, String contentSender, String channel) throws Exception {
        Notification notification = new Notification("Approve Job: "+ job.getName(), contentSender, channel , false, receiver.getId());
        notification.setCreateBy(sender);
        notification.setUpdateBy(sender);
        notificationRepository.save(notification);

    }

    @Override
    public void createNotificationRejectReqForWorker(Job job, User sender, User receiver, String contentSender, String channel) throws Exception {
        Notification notification = new Notification("Reject Job: "+ job.getName(), contentSender, channel , false, receiver.getId());
        notification.setCreateBy(sender);
        notification.setUpdateBy(sender);
        notificationRepository.save(notification);
    }

    @Override
    public void createNotificationReqRating(Job job, User sender, User receiver, String contentSender, String channel) throws Exception {
        Notification notification = new Notification("Reject Job: "+ job.getName(), contentSender, channel , false, receiver.getId());
        notification.setCreateBy(sender);
        notification.setUpdateBy(sender);
        notificationRepository.save(notification);
    }

    @Override
    public long getNumNotifyPushUnReadByUser(HttpServletRequest request) throws Exception {
        User receiver = authService.getUserByHeader(request);
        if (receiver == null){
            throw new Exception("User receiver not found!");
        }
        return notificationRepository.countByToUserAndChannelAndIsRead(receiver.getId(),"push",false);
    }

    @Override
    public void markAllNotifyIsReadByUser(HttpServletRequest request) throws Exception {
        User receiver = authService.getUserByHeader(request);
        if (receiver == null){
            throw new Exception("User receiver not found!");
        }
        List<Notification> notifications = notificationRepository.findByToUserAndChannelAndIsRead(receiver.getId(),"push",false);
        notifications.forEach(i->i.setIsRead(true));
        notificationRepository.saveAll(notifications);
    }

    @Override
    public List<Notification> getNotifyByUser(HttpServletRequest request, int page, int size) throws Exception {
        User receiver = authService.getUserByHeader(request);
        if (receiver == null){
            throw new Exception("User receiver not found!");
        }
        Pageable pageable = PageRequest.of(page,size);
        return notificationRepository.findByToUserAndChannelOrderByCreateAtDesc(receiver.getId(),"push",pageable);
    }
}

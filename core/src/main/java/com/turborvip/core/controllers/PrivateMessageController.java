package com.turborvip.core.controllers;

import com.turborvip.core.domain.repositories.UserRepository;
import com.turborvip.core.model.dto.Message;
import com.turborvip.core.model.dto.MessageJob;
import com.turborvip.core.model.dto.PrivateMessage;
import com.turborvip.core.model.entity.User;
import com.turborvip.core.service.JobService;
import io.lettuce.core.ScriptOutputType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class PrivateMessageController {

    @Autowired
    JobService jobService;

    @Autowired
    UserRepository userRepository;

    private final SimpMessageSendingOperations messagingTemplate;

    public PrivateMessageController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/private-message/{recipientId}")
    public void sendPrivateMessage(@DestinationVariable String recipientId, PrivateMessage message) {
        // Xử lý tin nhắn ở đây
        Long senderId = message.getSender();
        String content = message.getContent();

        System.out.println("senderId : " + senderId + " recipientId : " + recipientId + " : " + content);
        // Gửi tin nhắn đến người nhận
        messagingTemplate.convertAndSendToUser(recipientId, "/private-message", new Message(senderId, content));
    }

    @MessageMapping("/private-message/send-request-apply-job/{jobId}")
    public void getJobsRuntime(@DestinationVariable String jobId, PrivateMessage message) throws Exception {
        messagingTemplate.convertAndSend("/topic/private-message/get-request-apply-job/" + jobId,
                new Message(message.getSender(), "Update apply request runtime", true));
    }

    @MessageMapping("/private-message/send-request-update-notify/{userId}")
    public void getNotifyRuntime(@DestinationVariable String userId, PrivateMessage message) throws Exception {

        System.out.println("userId :" +userId);
        if(Objects.equals(userId, "null")){
            long userIdDB = jobService.findUserIdByJobId(message.getJobId());
            messagingTemplate.convertAndSend("/topic/private-message/get-request-update-notify/" + userIdDB,
                    new Message(message.getSender(), "Update list notify runtime", true));
        }else{
            messagingTemplate.convertAndSend("/topic/private-message/get-request-update-notify/" + userId,
                    new Message(message.getSender(), "Update list notify runtime", true));
        }
    }
}
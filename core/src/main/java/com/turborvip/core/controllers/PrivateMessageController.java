package com.turborvip.core.controllers;

import com.turborvip.core.domain.repositories.UserRepository;
import com.turborvip.core.model.dto.Message;
import com.turborvip.core.model.dto.MessageJob;
import com.turborvip.core.model.dto.PrivateMessage;
import com.turborvip.core.model.entity.User;
import com.turborvip.core.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

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

    @MessageMapping("/private-message/get-job/{recipientId}")
    public MessageJob getJobsRuntime(@DestinationVariable String recipientId, PrivateMessage message) throws Exception {
        User user = userRepository.findById(message.getSender()).orElse(null);
        if(user != null){
            messagingTemplate.convertAndSendToUser(recipientId, "/topic/private-message/get-job",
                    new MessageJob(message.getSender(), "Update jobs runtime",  jobService.getRunTimeJob(user,0, 10,message.getLng(),message.getLat())));
        }
        return null;
    }
}
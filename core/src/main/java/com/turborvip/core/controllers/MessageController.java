package com.turborvip.core.controllers;

import com.turborvip.core.domain.http.response.JobsResponse;
import com.turborvip.core.domain.repositories.UserRepository;
import com.turborvip.core.model.dto.Message;
import com.turborvip.core.model.dto.MessageJob;
import com.turborvip.core.model.entity.User;
import com.turborvip.core.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    JobService jobService;

    @Autowired
    UserRepository userRepository;

    @MessageMapping("/chat") // Mapping URL của message từ client
    @SendTo("/topic/messages") // Mapping địa chỉ để gửi lại message cho client
    public Message sendMessage(Message message) {
        System.out.println(message.getContent());
        return new Message(0L, "Echo: " + message.getContent());
    }

    @MessageMapping("/find/job-now")
    @SendTo("/topic/request-update-job-runtime") // Mapping địa chỉ để gửi lại message cho client
    public Message findJobNow(Message message) throws Exception {
        User user = userRepository.findById(message.getSender()).orElse(null);
        if(user != null){
            jobService.findNormalJobRunTime(user, message.getJobId());
            return new Message(message.getSender(), "Update jobs runtime", true);
        }
        return new Message(message.getSender(), "Update jobs runtime");
    }
}

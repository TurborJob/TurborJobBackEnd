package com.turborvip.core.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    private Long sender;
    private String content;

    private Double lat = null;
    private Double lng = null;

    private Long userId = null;

    private Long jobId = null;

    private Boolean isSuccess = false;

    public Message(Long sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public Message(Long sender, String content, Boolean isSuccess) {
        this.sender = sender;
        this.content = content;
        this.isSuccess = isSuccess;
    }
}
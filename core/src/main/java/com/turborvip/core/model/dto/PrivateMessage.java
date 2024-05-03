package com.turborvip.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrivateMessage {

    private Long sender;
    private Long recipientId;
    private String content;

    private Long jobId = null;

    private Boolean isSuccess = false;

    private Double lat = null;
    private Double lng = null;

}

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
public class PrivateMessage {

    private Long sender;
    private Long recipientId;
    private String content;

    private Long jobId = null;

    private Boolean isSuccess = false;

    private Double lat = null;
    private Double lng = null;

}

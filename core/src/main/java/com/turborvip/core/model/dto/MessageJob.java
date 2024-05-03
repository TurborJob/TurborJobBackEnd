package com.turborvip.core.model.dto;

import com.turborvip.core.domain.http.response.JobResponse;
import com.turborvip.core.domain.http.response.JobsResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageJob {

    private Long sender;
    private String content;

    private JobsResponse jobs = null;

    public MessageJob(Long sender, String content) {
        this.sender = sender;
        this.content = content;
    }


}
package com.turborvip.core.domain.http.response;

import com.turborvip.core.model.entity.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingHistoryResponse {
    private List<UserJobInfo> userJob;
    private long total;
}

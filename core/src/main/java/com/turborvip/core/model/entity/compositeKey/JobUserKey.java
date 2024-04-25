package com.turborvip.core.model.entity.compositeKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class JobUserKey {
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "user_id")
    private Long userId;
}

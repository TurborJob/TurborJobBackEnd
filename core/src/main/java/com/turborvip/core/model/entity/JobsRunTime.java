package com.turborvip.core.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;


@RedisHash("JobsRunTime")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobsRunTime implements Serializable {

    @Id
    @Column(name = "jobId")
    private long id;

    private String recipientId;

}

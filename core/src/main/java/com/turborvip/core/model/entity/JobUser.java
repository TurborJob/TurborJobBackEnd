package com.turborvip.core.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.turborvip.core.model.entity.base.AbstractBaseWithoutId;
import com.turborvip.core.model.entity.compositeKey.JobUserKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "job_user", schema = "job")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobUser extends AbstractBaseWithoutId {
    @EmbeddedId
    JobUserKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User userId;

    @ManyToOne
    @MapsId("jobId")
    @JoinColumn(name = "jobId")
    private Job jobId;

    String status;
    // pending, approve, reject.

    @Column(name = "description", columnDefinition = "TEXT")
    String description = null;

}

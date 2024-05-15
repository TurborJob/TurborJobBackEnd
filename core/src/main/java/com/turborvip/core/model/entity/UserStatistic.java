package com.turborvip.core.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.turborvip.core.model.entity.base.AbstractBase;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "user_statistic", schema = "history")
public class UserStatistic extends AbstractBase {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;

    @Column(name = "user_approve_day", nullable = false)
    private long userApproveInDay = 0;

    @Column(name = "job_success_day", nullable = false)
    private long jobSuccessInDay = 0;

    @Column(name = "user_approve_month", nullable = false)
    private long userApproveInMonth = 0;

    @Column(name = "job_success_month", nullable = false)
    private long jobSuccessInMonth = 0;

    @Column(name = "user_approve", nullable = false)
    private long userApprove = 0;

    @Column(name = "job_success", nullable = false)
    private long jobSuccess = 0;
}

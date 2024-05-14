package com.turborvip.core.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.turborvip.core.model.entity.base.AbstractBase;
import com.turborvip.core.model.entity.base.AbstractBaseWithoutId;
import com.turborvip.core.model.entity.compositeKey.RateHistoryKey;
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
@Table(name = "rate_history", schema = "history")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RateHistory extends AbstractBase {

    @ManyToOne
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JoinColumn(name = "to_user_id")
    private User toUser;

    private String jobName;

    @Column(nullable = true)
    Float ratingPoint;

    @Column(name = "description", columnDefinition = "TEXT")
    String description = null;

}

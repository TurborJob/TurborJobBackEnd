package com.turborvip.core.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.turborvip.core.model.entity.base.AbstractBase;
import com.turborvip.core.model.entity.base.AbstractBaseWithoutId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rate_history", schema = "history")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RateHistory extends AbstractBaseWithoutId {
    @EmbeddedId
    RateHistoryKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "to_user_id")
    private User toUser;

    float ratingPoint;

    @Column(name = "description", columnDefinition = "TEXT")
    String description = null;

}

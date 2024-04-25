package com.turborvip.core.model.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.turborvip.core.model.entity.base.AbstractBase;
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
@Table(name = "notification", schema = "history")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notification extends AbstractBase {

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @NotEmpty(message = "Type must not be empty")
    @Column(name = "channel")
    private String channel = "push";
    // push, sms, mail

    @Column(name = "is_read")
    private Boolean isRead = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "send_to", referencedColumnName = "id")
    private User toUser;
}

package com.turborvip.core.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.turborvip.core.model.entity.base.AbstractBase;
import com.turborvip.core.model.entity.base.AbstractBaseWithoutId;
import com.turborvip.core.model.entity.compositeKey.UserRoleKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_roles", schema = "account")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRole extends AbstractBase {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonInclude
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonInclude
    @JsonIgnore
    private Role role;

    @Column(name = "due_date")
    private Date dueDate;
}

package com.turborvip.core.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.turborvip.core.constant.EnumRole;
import com.turborvip.core.model.entity.base.AbstractBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Table(name = "roles",schema = "account")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role extends AbstractBase {

    @Enumerated(EnumType.STRING)
    @Column(name = "code",unique = true)
    private EnumRole code;

    @Column(name = "name",unique = true)
    private String name;

//    @OneToMany(mappedBy = "roles", cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
//    @JsonInclude
//    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "role", cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    @JsonInclude
    @JsonIgnore
    private Set<UserRole> userRole = new HashSet<>();

    public Role(EnumRole enumRole, String name) {
        this.code = EnumRole.valueOf(enumRole.name());
        this.name = name;
    }

    public Role(EnumRole enumRole) {
        this.code = EnumRole.valueOf(enumRole.name());
    }
}

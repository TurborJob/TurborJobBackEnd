package com.turborvip.core.model.entity.compositeKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleKey {
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "user_id")
    private Long userId;
}

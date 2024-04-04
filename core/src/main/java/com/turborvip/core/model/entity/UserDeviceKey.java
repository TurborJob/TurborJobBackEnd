package com.turborvip.core.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserDeviceKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "device_id")
    private Long deviceId;
}

package com.turborvip.core.service;

import com.turborvip.core.model.entity.UserDevice;

import java.util.Optional;

public interface UserDeviceService {
    Optional<UserDevice> findDeviceByUserIdAndDeviceId(Long userId, String deviceId);

    UserDevice create(UserDevice userDevice);
}

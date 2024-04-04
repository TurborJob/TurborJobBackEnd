package com.turborvip.core.service;

import com.turborvip.core.model.entity.Device;

import java.sql.Timestamp;

public interface DeviceService {
    Device updateLastLogin(Timestamp lastLoginAt, Long deviceId) throws Exception;

    Device findDeviceByUserAgent(String userAgent);

    Device create(Device device);

}

package com.turborvip.core.service.impl;

import com.turborvip.core.constant.DevMessageConstant;
import com.turborvip.core.domain.repositories.DeviceRepository;
import com.turborvip.core.service.DeviceService;
import com.turborvip.core.model.entity.Device;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    @Override
    public Device updateLastLogin(Timestamp lastLoginAt, Long deviceId) throws Exception {
        try {
            Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new Exception(DevMessageConstant.Common.EXIST_DEVICE));
            device.setLastLoginAt(lastLoginAt);
            return device;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw  e;
        }
    }

    @Override
    public Device findDeviceByUserAgent(String userAgent) {
        return deviceRepository.findByUserAgent(userAgent);
    }

    @Override
    public Device create(Device device) {
        return deviceRepository.save(device);
    }
}

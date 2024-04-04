package com.turborvip.core.service.impl;

import com.turborvip.core.domain.repositories.UserDeviceRepository;
import com.turborvip.core.service.UserDeviceService;
import com.turborvip.core.model.entity.UserDevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserDeviceServiceImpl implements UserDeviceService {
    @Autowired
    UserDeviceRepository userDeviceRepository;

    @Override
    public Optional<UserDevice> findDeviceByUserIdAndDeviceId(Long userId, String deviceId) {
        return userDeviceRepository.findById_UserIdAndDevice_UserAgent(userId,deviceId);
    }

    @Override
    public UserDevice create(UserDevice userDevice) {
        return userDeviceRepository.save(userDevice);
    }
}

package com.turborvip.core.domain.repositories;

import com.turborvip.core.model.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    Optional<UserDevice> findById_UserIdAndDevice_UserAgent(Long userId, String userAgent);



}

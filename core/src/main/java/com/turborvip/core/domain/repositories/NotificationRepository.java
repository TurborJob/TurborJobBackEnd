package com.turborvip.core.domain.repositories;

import com.turborvip.core.model.entity.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByToUserAndChannelAndIsRead(Long toUser, String channel, Boolean isRead);

    long countByToUserAndChannelAndIsRead(Long toUser, String channel, Boolean isRead);

    List<Notification> findByToUserAndChannelOrderByCreateAtDesc(Long toUser, String channel, Pageable pageable);

}

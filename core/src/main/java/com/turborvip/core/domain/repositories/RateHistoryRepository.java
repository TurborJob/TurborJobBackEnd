package com.turborvip.core.domain.repositories;

import com.turborvip.core.model.entity.RateHistory;
import com.turborvip.core.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateHistoryRepository extends JpaRepository<RateHistory, Long> {
    List<RateHistory> findByToUser(User toUser);

    int countByToUser(User toUser);

}

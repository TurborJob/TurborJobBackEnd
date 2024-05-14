package com.turborvip.core.domain.repositories;

import com.turborvip.core.model.entity.RateHistory;
import com.turborvip.core.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateHistoryRepository extends JpaRepository<RateHistory, Long> {
    List<RateHistory> findByToUser(User toUser);

    int countByToUser(User toUser);

    List<RateHistory> findByFromUserAndRatingPointOrderByCreateAtDesc(User fromUser, Float ratingPoint, Pageable pageable);

    long countByFromUserAndRatingPoint(User fromUser, Float ratingPoint);

    Optional<RateHistory> findByFromUserAndToUser(User fromUser, User toUser);

    long countByFromUser(User fromUser);

    List<RateHistory> findByToUserAndRatingPointNotNull(User toUser);


}

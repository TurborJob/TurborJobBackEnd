package com.turborvip.core.service.impl;

import com.turborvip.core.domain.http.response.RatingHistoryResponse;
import com.turborvip.core.domain.http.response.UserJobInfo;
import com.turborvip.core.domain.repositories.RateHistoryRepository;
import com.turborvip.core.domain.repositories.UserRepository;
import com.turborvip.core.model.entity.RateHistory;
import com.turborvip.core.model.entity.User;
import com.turborvip.core.service.RatingHistoryService;
import com.turborvip.core.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RatingHistoryServiceImpl implements RatingHistoryService {

    @Autowired
    private RateHistoryRepository rateHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final AuthService authService;

    @Autowired
    private final UserService userService;

    @Override
    public RatingHistoryResponse getRating(HttpServletRequest request, int page, int size) throws Exception {
        User fromUser = authService.getUserByHeader(request);
        Pageable pageable = PageRequest.of(page, size);
        long numRateHistory = rateHistoryRepository.countByFromUserAndRatingPoint(fromUser, null);
        List<RateHistory> rateHistoryList = rateHistoryRepository.findByFromUserAndRatingPointOrderByCreateAtDesc(fromUser, null, pageable);

        List<UserJobInfo> userJobInfo = new ArrayList<>();

        rateHistoryList.forEach(i -> userJobInfo.add(new UserJobInfo(i.getToUser().getProfileAndNote(null), i.getJobName())));

        return new RatingHistoryResponse(userJobInfo, numRateHistory);
    }

    @Override
    public void rateUser(HttpServletRequest request, float value, String note, long toUser) throws Exception {
        User userFrom = authService.getUserByHeader(request);
        User userTo = userRepository.findById(toUser).orElse(null);
        if (userTo == null){
            throw new Exception("User receiver not found!");
        }
        RateHistory rateHistory = rateHistoryRepository.findByFromUserAndToUser(userFrom, userTo).orElse(null);
        if (rateHistory == null){
            throw new Exception("Rate History not found!");
        }
        rateHistory.setRatingPoint(value);
        rateHistory.setDescription(note);
        rateHistoryRepository.save(rateHistory);
        userService.updateUserAfterRate(userTo);
    }
}

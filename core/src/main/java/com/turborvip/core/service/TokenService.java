package com.turborvip.core.service;

import com.turborvip.core.model.entity.Token;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface TokenService {
    Token create(Token token);

    Optional<Token> findFirstTokenByUserIdAndNameAndDeviceId(Long userId, String name, String DeviceId);

    Token updateTokenWithValueExpiredTime(Token tokenOld,Timestamp updateAt, String value, Timestamp expiredAt,String verifyKey,String tokenUsed);

    List<Token> findListTokenByUserAndDevice(Long userId,String deviceId);

    Optional<Token> findFirstTokenByValue(String value);

    Optional<Token> findTokenByValueAndNameAndType(String tokenValue,String name ,String type);

    List<Token> findListTokenExpired();

    Optional<Token> findByRefreshTokenUsed(String refreshToken);

    List<Token> findByUserId(Long userId);

}

package com.turborvip.core.service.impl;

import com.turborvip.core.config.exception.VsException;
import com.turborvip.core.constant.CommonConstant;
import com.turborvip.core.constant.DevMessageConstant;
import com.turborvip.core.domain.adapter.web.base.RestData;
import com.turborvip.core.domain.adapter.web.base.VsResponseUtil;
import com.turborvip.core.domain.http.request.AuthRequest;
import com.turborvip.core.domain.http.request.ChangePassRequest;
import com.turborvip.core.domain.http.response.AuthResponse;
import com.turborvip.core.domain.repositories.TokenRepository;
import com.turborvip.core.domain.repositories.UserRepository;
import com.turborvip.core.domain.repositories.UserRoleRepository;
import com.turborvip.core.model.entity.*;
import com.turborvip.core.service.TokenService;
import com.turborvip.core.util.RegexValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static com.turborvip.core.constant.DevMessageConstant.Common.LOGIN_SUCCESS;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.USER_AGENT;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;
    private final TokenService tokenService;

    @Autowired
    private final TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public ResponseEntity<RestData<?>> authenticate(AuthRequest authRequest, HttpServletRequest request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            User user = null;
            if (authRequest.getUsername() != null) {
                user = userRepository.findByUsername(authRequest.getUsername()).orElse(null);
            }

            List<Role> roleDB = new ArrayList<>(List.of());
            if (user != null) {
                if(Objects.equals(user.getStatus(), "lock")){
                    throw new Exception("User is locked, please contact Admin to unlock!");
                }
                List<UserRole> userRole = userRoleRepository.findByUser(user);
                LocalDate currentDate = LocalDate.now();
                userRole.forEach(i -> {
                    if (i.getDueDate() == null) {
                        roleDB.add(i.getRole());
                    } else {
                        LocalDate dueDate = i.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        if (dueDate.isAfter(currentDate)) {
                            roleDB.add(i.getRole());
                        }
                    }
                });
            }

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (roleDB.isEmpty()) {
                throw new Exception("Role is null");
            }

            roleDB.forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getCode().name())));

            String DEVICE_ID = request.getHeader(USER_AGENT);
            List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).toList();
            var jwtToken = jwtService.generateToken(user, roles, DEVICE_ID);
            var jwtRefreshToken = jwtService.generateRefreshToken(user, roles, DEVICE_ID, null);
            AuthResponse authResponse = new AuthResponse(jwtToken, jwtRefreshToken, user.getProfile());
            return VsResponseUtil.ok(LOGIN_SUCCESS, authResponse);
        } catch (Exception err) {
            log.warn(err.getMessage());
            throw err;
        }
    }

    public void changePass(ChangePassRequest changePassRequest, HttpServletRequest request) throws Exception {
        try {
            String DEVICE_ID = request.getHeader(USER_AGENT);
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            User user = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring("Bearer ".length());
                Token tokenExist = tokenService.findFirstTokenByValue(token).orElse(null);
                if (tokenExist != null) {
                    List<Token> listToken = tokenService.findListTokenByUserAndDevice(tokenExist.getCreateBy().getId(), DEVICE_ID);
                    if (listToken.isEmpty()) {
                        throw new Exception("Token not found");
                    }
                    UserDevice userDevice = listToken.get(0).getUserDevices();
                    user = userDevice.getUser();
                } else {
                    throw new Exception("Token not found");
                }
            }

            if (user == null) {
                throw new Exception("User not found!");
            }

            /* Do compare password */
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(changePassRequest.getPassword(), user.getPassword())) {
                throw new Exception("Old password is wrong");
            }

            RegexValidator checkPassword = new RegexValidator(CommonConstant.REGEX_PASSWORD);
            if (!checkPassword.validate(changePassRequest.getNewPassword())) {
                throw new VsException(DevMessageConstant.Common.PASSWORD_WRONG_FORMAT);
            }
            user.setPassword(new BCryptPasswordEncoder().encode(changePassRequest.getNewPassword()));
            userRepository.save(user);
        } catch (Exception err) {
            log.warn(err.getMessage());
            throw err;
        }
    }

    public void removeToken(HttpServletRequest request) throws IOException {
        String DEVICE_ID = request.getHeader(USER_AGENT);
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());
            Token tokenExist = tokenService.findFirstTokenByValue(token).orElse(null);
            if (tokenExist != null) {
                List<Token> listToken = tokenService.findListTokenByUserAndDevice(tokenExist.getCreateBy().getId(), DEVICE_ID);
                listToken.forEach(tokenRepository::delete);
            }
        }
    }

    public User getUserByHeader(HttpServletRequest request) throws Exception {
        String DEVICE_ID = request.getHeader(USER_AGENT);
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        User user = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());
            Token tokenExist = tokenService.findFirstTokenByValue(token).orElse(null);
            if (tokenExist != null) {
                List<Token> listToken = tokenService.findListTokenByUserAndDevice(tokenExist.getCreateBy().getId(), DEVICE_ID);
                if (listToken.isEmpty()) {
                    throw new Exception("Token not found");
                }
                UserDevice userDevice = listToken.get(0).getUserDevices();
                user = userDevice.getUser();
            } else {
                throw new Exception("Token not found");
            }
        }

        if (user == null) {
            throw new Exception("User not found!");
        }
        return user;
    }

    public void forgotPass(String username) throws Exception {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new Exception("Account not found!");
        }

        // Todo remove all token
        List<Token> listByUser = tokenService.findByUserId(user.getId());
        listByUser.forEach(tokenRepository::delete);

        String randomPassword = RandomStringUtils.random(7, true, true) + "!";

        user.setPassword(new BCryptPasswordEncoder().encode(randomPassword));
        userRepository.save(user);

        new GMailerServiceImpl().sendEmail(user.getEmail(),
                "RESET PASSWORD - TURBORJOB",
                "New Pass : " + randomPassword);
    }

}

package com.turborvip.core.service.impl;

import com.turborvip.core.config.exception.VsException;
import com.turborvip.core.constant.CommonConstant;
import com.turborvip.core.constant.DevMessageConstant;
import com.turborvip.core.constant.EnumRole;
import com.turborvip.core.domain.http.request.UpdateProfileRequest;
import com.turborvip.core.domain.repositories.RateHistoryRepository;
import com.turborvip.core.domain.repositories.RoleRepository;
import com.turborvip.core.domain.repositories.UserRepository;
import com.turborvip.core.model.dto.Profile;
import com.turborvip.core.model.entity.*;
import com.turborvip.core.model.entity.compositeKey.RateHistoryKey;
import com.turborvip.core.service.TokenService;
import com.turborvip.core.service.UserService;
import com.turborvip.core.util.RegexValidator;
import com.turborvip.core.model.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.USER_AGENT;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final RateHistoryRepository rateHistoryRepository;

    @Autowired
    private final TokenService tokenService;

    @Autowired
    private final AuthService authService;


    @Override
    public User create(User user, HttpServletRequest request) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User registerUser(UserDTO userDTO) {
        try {
            // Todo check birthday
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.FORMAT_DATE_PATTERN);
            Date birthday = null;
            if (userDTO.getBirthday() != null) {
                try {
                    birthday = sdf.parse(userDTO.getBirthday());
                } catch (Exception e) {
                    log.error(e.getMessage());
                    throw new VsException(String.format(e.getMessage()));
                }
            }

            // Todo check phone number
            if (userDTO.getPhone() != null) {
                RegexValidator checkPhone = new RegexValidator(CommonConstant.REGEX_PHONE_NUMBER);
                if (!checkPhone.validate(userDTO.getPhone())) {
                    throw new VsException(DevMessageConstant.Common.PHONE_WRONG_FORMAT);
                }
            }

            // Todo check email number
            if (userDTO.getEmail() != null) {
                RegexValidator checkEmail = new RegexValidator(CommonConstant.REGEX_EMAIL);
                if (!checkEmail.validate(userDTO.getEmail())) {
                    throw new VsException(DevMessageConstant.Common.EMAIL_WRONG_FORMAT);
                }
            }

            // Todo check password empty
            if (userDTO.getPassword() == null) {
                throw new VsException(DevMessageConstant.Common.PASSWORD_NOT_EMPTY);
            }

            // Todo check password format
            RegexValidator checkPassword = new RegexValidator(CommonConstant.REGEX_PASSWORD);
            if (!checkPassword.validate(userDTO.getPassword())) {
                throw new VsException(DevMessageConstant.Common.PASSWORD_WRONG_FORMAT);
            }

            // Todo check exist
            User userExist = userRepository.findFirstByUsernameOrEmailOrPhone(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPhone()).orElse(null);
            if (userExist != null) {
                if (Objects.equals(userExist.getUsername(), userDTO.getUsername())) {
                    throw new VsException(String.format(DevMessageConstant.Common.EXITS_USERNAME));
                }
                if (Objects.equals(userExist.getPhone(), userDTO.getPhone())) {
                    throw new VsException(String.format(DevMessageConstant.Common.EXITS_PHONE));
                }
                if (Objects.equals(userExist.getEmail(), userDTO.getEmail())) {
                    throw new VsException(String.format(DevMessageConstant.Common.EXITS_EMAIL));
                }
            }

            // Todo create new user
            Role roleUser = roleRepository.findRoleByCode(EnumRole.ROLE_USER);
            User user = new User(userDTO.getFullName(), userDTO.getUsername(), new BCryptPasswordEncoder().encode(userDTO.getPassword()),
                    userDTO.getEmail(), birthday, userDTO.getGender(), userDTO.getPhone(), userDTO.getAddress(), userDTO.getAvatar(), 5,0, 0, new HashSet<>());
            user = userRepository.save(user);
            user.getRoles().add(roleUser);

            return user;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addToUser(String username, String role_name) {

        User user = userRepository.findByUsername(username).get();
        Role role = roleRepository.findRoleByCode(EnumRole.valueOf(role_name));

        user.getRoles().add(role);

        userRepository.save(user);
    }

    @Override
    public void ratedUser(Long fromUserId, Long toUserId, float value, String description) {
        RateHistoryKey rateHistoryKey = new RateHistoryKey(fromUserId, toUserId);
        User fromUser = userRepository.findById(fromUserId).orElse(null);
        User toUser = userRepository.findById(toUserId).orElse(null);
        if (fromUser != null && toUser != null) {
            RateHistory rateHistory = new RateHistory(rateHistoryKey, fromUser, toUser, value, description);
            rateHistoryRepository.save(rateHistory);
        }

        List<RateHistory> listRatedUser = rateHistoryRepository.findByToUser(toUser);
        float ratePointUser = 0;
        float averageRatePoint = 5;

        if (!listRatedUser.isEmpty()) {
            for (RateHistory rateHistory : listRatedUser) {
                ratePointUser += rateHistory.getRatingPoint();
            }
            averageRatePoint = ratePointUser / listRatedUser.size();

            if (toUser != null) {
                toUser.setRating(averageRatePoint);
                toUser.setCountRate(listRatedUser.size());
                userRepository.save(toUser);
            }

        }

    }

    @Override
    public int numberUserRateFollowUser(Long userId) throws Exception {
        User toUser = userRepository.findById(userId).orElse(null);
        if (toUser == null) {
            throw new Exception("User not found");
        }
        return rateHistoryRepository.countByToUser(toUser);
    }

    @Override
    public List<Role> getRoleName(HttpServletRequest request) throws Exception {
        User user = authService.getUserByHeader(request);

        List<Role> roles = user.getRoles().stream().toList();

        return roles;
    }

    @Override
    public Profile updateProfile(HttpServletRequest request, UpdateProfileRequest updateProfileRequest) throws Exception {
        try {
            String DEVICE_ID = request.getHeader(USER_AGENT);
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            User user = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring("Bearer ".length());
                Token tokenExist = tokenService.findFirstTokenByValue(token).orElse(null);
                if (tokenExist != null) {
                    List<Token> listToken = tokenService.findListTokenByUserAndDevice(tokenExist.getCreateBy().getId(), DEVICE_ID);
                    if(listToken.isEmpty()){
                        throw new Exception("Token not found");
                    }
                    UserDevice userDevice = listToken.get(0).getUserDevices();
                    user = userDevice.getUser();
                }else{
                    throw new Exception("Token not found");
                }
            }

            if(user == null){
                throw new Exception("User not found!");
            }

            // Todo check birthday
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.FORMAT_DATE_PATTERN);
            Date birthday = null;
            if (updateProfileRequest.getBirthday() != null) {
                try {
                    birthday = sdf.parse(updateProfileRequest.getBirthday());
                } catch (Exception e) {
                    log.error(e.getMessage());
                    throw new VsException(String.format(e.getMessage()));
                }
            }

            // Todo check phone number
            if (updateProfileRequest.getPhone() != null) {
                RegexValidator checkPhone = new RegexValidator(CommonConstant.REGEX_PHONE_NUMBER);
                if (!checkPhone.validate(updateProfileRequest.getPhone())) {
                    throw new VsException(DevMessageConstant.Common.PHONE_WRONG_FORMAT);
                }
            }

            // Todo check email number
            if (updateProfileRequest.getEmail() != null) {
                RegexValidator checkEmail = new RegexValidator(CommonConstant.REGEX_EMAIL);
                if (!checkEmail.validate(updateProfileRequest.getEmail())) {
                    throw new VsException(DevMessageConstant.Common.EMAIL_WRONG_FORMAT);
                }
            }

            // Todo check exist
            User userExist = userRepository.findByEmailAndPhoneAndIdNot(updateProfileRequest.getEmail(), updateProfileRequest.getPhone(), user.getId()).orElse(null);
            if (userExist != null) {
                if (Objects.equals(userExist.getPhone(), updateProfileRequest.getPhone())) {
                    throw new VsException(String.format(DevMessageConstant.Common.EXITS_PHONE));
                }
                if (Objects.equals(userExist.getEmail(), updateProfileRequest.getEmail())) {
                    throw new VsException(String.format(DevMessageConstant.Common.EXITS_EMAIL));
                }
            }
            user.setFullName(updateProfileRequest.getFullName());
            user.setAddress(updateProfileRequest.getAddress());
            user.setAvatar(updateProfileRequest.getAvatar());
            user.setBirthday(birthday);
            user.setEmail(updateProfileRequest.getEmail());
            user.setGender(updateProfileRequest.getGender());
            user.setPhone(updateProfileRequest.getPhone());
            return userRepository.save(user).getProfile();
        } catch (Exception err) {
            log.warn(err.getMessage());
            throw err;
        }
    }

    @Override
    public void updateBusiness(HttpServletRequest request) throws Exception {
        try{
            User user = authService.getUserByHeader(request);
            addToUser(user.getUsername(), String.valueOf(EnumRole.MANAGER));
        } catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }


}

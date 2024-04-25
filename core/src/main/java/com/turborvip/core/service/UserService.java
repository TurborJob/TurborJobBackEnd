package com.turborvip.core.service;

import com.turborvip.core.domain.http.request.UpdateProfileRequest;
import com.turborvip.core.model.dto.Profile;
import com.turborvip.core.model.dto.UserDTO;
import com.turborvip.core.model.entity.Role;
import com.turborvip.core.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface UserService {
    User create(User user, HttpServletRequest request);

    User registerUser(UserDTO userDTO);

    Optional<User> findById(Long id);

    Role saveRole(Role role);

    void addToUser(String username, String role_name, Date dueDate);

    void ratedUser(Long fromUserId, Long toUserId, float value, String description );

    int numberUserRateFollowUser(Long userId) throws Exception;

    List<Role> getRoleName(HttpServletRequest request) throws Exception;

    Profile updateProfile(HttpServletRequest request, UpdateProfileRequest updateProfileRequest) throws Exception;

    void updateBusiness(HttpServletRequest request) throws Exception;
}

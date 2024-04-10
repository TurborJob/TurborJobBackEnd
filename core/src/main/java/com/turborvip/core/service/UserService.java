package com.turborvip.core.service;

import com.turborvip.core.model.dto.UserDTO;
import com.turborvip.core.model.entity.Role;
import com.turborvip.core.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;


public interface UserService {
    User create(User user, HttpServletRequest request);

    User registerUser(UserDTO userDTO);

    Optional<User> findById(Long id);

    Role saveRole(Role role);

    void addToUser(String username, String role_name);

    void ratedUser(Long fromUserId, Long toUserId, float value, String description );

    int numberUserRateFollowUser(Long userId) throws Exception;

    String getRoleNameByPhone(String phone) throws Exception;

    void changePass(HttpServletRequest request, String newPass);
}

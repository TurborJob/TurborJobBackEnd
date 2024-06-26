package com.turborvip.core.service;

import com.turborvip.core.domain.http.request.UpdateProfileRequest;
import com.turborvip.core.domain.http.response.AccountsResponse;
import com.turborvip.core.model.dto.AdminStatistic;
import com.turborvip.core.model.dto.BusinessDTO;
import com.turborvip.core.model.dto.Profile;
import com.turborvip.core.model.dto.UserDTO;
import com.turborvip.core.model.entity.RateHistory;
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

    void addToUser(String username, String role_name) throws Exception;

    void updateUserAfterRate(User toUser);

    int numberUserRateFollowUser(Long userId) throws Exception;

    List<Role> getRoleName(HttpServletRequest request) throws Exception;

    Profile updateProfile(HttpServletRequest request, UpdateProfileRequest updateProfileRequest) throws Exception;

    void updateBusiness(HttpServletRequest request) throws Exception;

    void extendRoleBusiness(User user, int numDayExtend, long limitJobInDay, long limitWorkerInDay) throws Exception;

    void checkLimitRoleBusiness(User user) throws Exception;

    BusinessDTO getBusinessStatistic(HttpServletRequest request) throws Exception;

    AdminStatistic getAdminStatistic() throws Exception;

    AccountsResponse getAccountByAdmin(int page, int size) throws Exception;

    void adminUpdateStatusUser(User admin, String status, long idUser) throws Exception;
}

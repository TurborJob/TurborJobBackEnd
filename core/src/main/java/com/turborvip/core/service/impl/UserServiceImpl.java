package com.turborvip.core.service.impl;

import com.turborvip.core.config.exception.VsException;
import com.turborvip.core.constant.CommonConstant;
import com.turborvip.core.constant.DevMessageConstant;
import com.turborvip.core.constant.EnumRole;
import com.turborvip.core.domain.http.request.UpdateProfileRequest;
import com.turborvip.core.domain.http.response.AccountsResponse;
import com.turborvip.core.domain.repositories.*;
import com.turborvip.core.model.dto.AdminStatistic;
import com.turborvip.core.model.dto.BusinessDTO;
import com.turborvip.core.model.dto.Profile;
import com.turborvip.core.model.entity.*;
import com.turborvip.core.service.ContactService;
import com.turborvip.core.service.TokenService;
import com.turborvip.core.service.UserDeviceService;
import com.turborvip.core.service.UserService;
import com.turborvip.core.util.RegexValidator;
import com.turborvip.core.model.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.USER_AGENT;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserDeviceRepository userDeviceRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final JobRepository jobRepository;

    @Autowired
    private final JobUserRepository jobUserRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final RateHistoryRepository rateHistoryRepository;

    @Autowired
    private final TokenService tokenService;

    @Autowired
    private final AuthService authService;

    @Autowired
    private final ContactService contactService;

    @Autowired
    private final UserDeviceService userDeviceService;



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
                    userDTO.getEmail(), birthday, userDTO.getGender(), userDTO.getPhone(), userDTO.getAddress(), userDTO.getAvatar(),
                    5,0, 0,userDTO.getLat(),userDTO.getLng(), new HashSet<>());
            user = userRepository.save(user);

            UserRole userRole = new UserRole(user, roleUser, null);

            userRoleRepository.save(userRole);
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
    public void addToUser(String username, String role_name, Date dueDate) {

        User user = userRepository.findByUsername(username).get();
        Role role = roleRepository.findRoleByCode(EnumRole.valueOf(role_name));
        UserRole userRole = new UserRole(user, role, dueDate);
        userRoleRepository.save(userRole);
    }

    @Override
    public void updateUserAfterRate(User toUser) {
        List<RateHistory> listRatedUser = rateHistoryRepository.findByToUserAndRatingPointNotNull(toUser);
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

        List<UserRole> userRole = userRoleRepository.findByUser(user);
        List<Role> roles = new ArrayList<>(List.of());
        LocalDate currentDate = LocalDate.now();
        userRole.forEach(i -> {
            if(i.getDueDate() == null){
                roles.add(i.getRole());
            }else{
                LocalDate dueDate = i.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (dueDate.isAfter(currentDate)){
                    roles.add(i.getRole());
                }
            }
        });

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

            if(updateProfileRequest.getLat() != null && updateProfileRequest.getLng() != null){
                GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
                user.setCoordinates(geometryFactory.createPoint(new Coordinate(updateProfileRequest.getLat(),updateProfileRequest.getLng())));
            }
            return userRepository.save(user).getProfile();
        } catch (Exception err) {
            log.warn(err.getMessage());
            throw err;
        }
    }

    @Override
    public void updateBusiness(HttpServletRequest request) throws Exception {
        try{
            Date currentDate = new Date();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, 30);
            Date futureDate = calendar.getTime();

            User user = authService.getUserByHeader(request);

            addToUser(user.getUsername(), String.valueOf(EnumRole.BUSINESS),futureDate);
        } catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public BusinessDTO getBusinessStatistic(HttpServletRequest request) throws Exception{
        User user = authService.getUserByHeader(request);
        long totalJob = jobRepository.countByCreateBy(user);
        long numJobSuccess = jobRepository.countByCreateByAndStatus(user,"success");
        long numJobInActive = jobRepository.countByCreateByAndStatus(user, "inactive");
        long numJobDone = jobRepository.countByCreateByAndStatus(user, "done");
        long numJobProcessing = jobRepository.countByCreateByAndStatus(user, "processing");
        long numJobFail = jobRepository.countByCreateByAndStatus(user,"fail");

        long totalWorkerReqApply = jobUserRepository.countByJobId_CreateBy(user);
        long numWorkerApprove = jobUserRepository.countByJobId_CreateByAndStatus(user, "approve");
        long numWorkerReject = jobUserRepository.countByJobId_CreateByAndStatus(user,"reject");
        long numWorkerPending = jobUserRepository.countByJobId_CreateByAndStatus(user, "pending");

        long totalRating = rateHistoryRepository.countByFromUser(user);
        long numRatingPending = rateHistoryRepository.countByFromUserAndRatingPoint(user,null);

        UserRole userRole  = userRoleRepository.findByUserAndRole_Code(user, EnumRole.BUSINESS).orElse(null);
        if(userRole == null){
            throw new Exception("You not business!");
        }

        return new BusinessDTO(totalJob,numJobSuccess,numJobDone,numJobFail,numJobProcessing,numJobInActive,totalWorkerReqApply
                ,numWorkerApprove,numWorkerReject, numWorkerPending,totalRating,numRatingPending,user.getRating(), user.getCountRate(),
                userRole.getDueDate());
    }

    @Override
    public AdminStatistic getAdminStatistic() throws Exception{
        long totalContacts = contactRepository.count();
        long numContactReplied = contactRepository.countByUserNotNull();
        long totalAccounts = userRepository.count();
        long numAccountWorker = userRoleRepository.countByRole_Code(EnumRole.ROLE_USER);
        long numAccountBusiness = userRoleRepository.countByRole_Code(EnumRole.BUSINESS);
        long numAccountAdmin = userRoleRepository.countByRole_Code(EnumRole.ROLE_ADMIN);
        long totalSessions = userDeviceRepository.count();
        long totalDevices = deviceRepository.count();
        return new AdminStatistic(totalContacts,numContactReplied,totalAccounts,numAccountWorker,numAccountBusiness,numAccountAdmin,totalSessions, totalDevices);
    }

    @Override
    public AccountsResponse getAccountByAdmin(int page,int size) throws Exception{
        Sort sort = Sort.by(Sort.Direction.DESC, "createAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<EnumRole> listRoleAdmin = new ArrayList<>();
        listRoleAdmin.add(EnumRole.ROLE_ADMIN);
        listRoleAdmin.add(EnumRole.ROLE_SUPER_ADMIN);
        long total = userRepository.countByUserRole_Role_CodeNotIn(listRoleAdmin);
        return new AccountsResponse(userRepository.findByUserRole_Role_CodeNotIn(listRoleAdmin, pageable), total);
    }

    @Override
    public void adminUpdateStatusUser(User admin, String status, long idUser) throws Exception{
        User user = userRepository.findById(idUser).orElse(null);
        if(user == null){
            throw new Exception("User invalid!");
        }
        Date now = new Date();
        user.setStatus(status);
        user.setUpdateBy(admin);
        user.setUpdateAt(new Timestamp(now.getTime()));
        userRepository.save(user);
    }
}

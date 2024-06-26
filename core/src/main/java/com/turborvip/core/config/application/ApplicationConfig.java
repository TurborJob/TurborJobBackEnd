package com.turborvip.core.config.application;

import com.turborvip.core.domain.repositories.UserRepository;
import com.turborvip.core.service.H3Service;
import com.turborvip.core.service.UserService;
import com.turborvip.core.service.UserStatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfig {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    UserStatisticService userStatisticService;


    @Autowired
    private final H3Service h3Service;


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            /*userService.saveRole(new Role(EnumRole.BUSINESS, "Business"));
            userService.saveRole(new Role(EnumRole.ROLE_USER,"User"));
            userService.saveRole(new Role(EnumRole.ROLE_SUPER_ADMIN,"Supper Admin"));
            userService.saveRole(new Role(EnumRole.ROLE_ADMIN,"Admin"));
            userService.saveRole(new Role(EnumRole.MANAGER, "Manager"));
            SimpleDateFormat formatter = new SimpleDateFormat(CommonConstant.FORMAT_DATE_PATTERN);
            Date now = new Date();

            HttpServletRequest request = null;
            User superAdmin = new User("TurborvipSuperAdmin", "turborvipSuperAdmin", "12345678aB!", "turborvipSuperAdmin@gmail.com", now, null, null, null, null,5,0,0, new HashSet<>());
            User user = new User("TurborvipUser", "turborvipUser", "12345678aB!", "turborvipUser@gmail.com", now, null, null, null, null,5,0,0, new HashSet<>());
            User admin = new User("TurborvipAdmin", "turborvipAdmin", "12345678aB!", "turborvipAdmin@gmail.com", now, null, null, null, null,5,0,0, new HashSet<>());
            User manager = new User("TurborvipManager", "turborvipManager", "12345678aB!", "turborvipManger@gmail.com", now, null, null, null, null,5,0,0, new HashSet<>());

            userService.create(superAdmin, request);
            User superAdminCreated = userService.findById(superAdmin.getId()).orElse(null);

            user.setCreateBy(superAdminCreated);
            user.setUpdateBy(superAdminCreated);
            admin.setCreateBy(superAdminCreated);
            admin.setUpdateBy(superAdminCreated);
            manager.setCreateBy(superAdminCreated);
            manager.setUpdateBy(superAdminCreated);

            userService.create(user, request);
            userService.create(admin, request);
            userService.create(manager, request);

            userService.addToUser("turborvipSuperAdmin", String.valueOf(EnumRole.ROLE_SUPER_ADMIN), null);
            userService.addToUser("turborvipUser", String.valueOf(EnumRole.ROLE_USER), null);
            userService.addToUser("turborvipAdmin", String.valueOf(EnumRole.ROLE_ADMIN), null);
            userService.addToUser("turborvipManager", String.valueOf(EnumRole.MANAGER), null);
             */
            //test

            //double km = h3Service.calculateDistance(new Coordinates(21.053130955287447, 105.73932319679818),new Coordinates(21.042175467219348, 105.7863161542057));
            //System.out.println("xxx " + km);


//            jobsRunTimeRepository.save(new JobsRunTime(2,"2" ));
//            jobsRunTimeRepository.save(new JobsRunTime(1,"2" ));
//            jobsRunTimeRepository.save(new JobsRunTime(3,"2" ));

//            jobsRunTimeRepository.deleteById("1");
//            jobsRunTimeRepository.deleteById("2");
//            jobsRunTimeRepository.deleteById("4");


//            JobsRunTime jobsRunTime = jobsRunTimeRepository.findById(1L).orElseThrow(null);
//            System.out.println(jobsRunTimeRepository.findByRecipientId("2"));
//            System.out.println(jobsRunTime.getRecipientId());

//            new GMailerServiceImpl().sendEmail("dothanhdat11032002@gmail.com",
//                    "Warning warning !!! Turborvip app",
//                    "Another try attach your account you should change password now!");

            userStatisticService.updateUserStatisticEndOfDay();
        };
    }

    @Bean
    public TaskScheduler taskScheduler() {
        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        return scheduler;
    }
}

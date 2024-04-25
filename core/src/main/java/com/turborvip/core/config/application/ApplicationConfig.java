package com.turborvip.core.config.application;

import com.turborvip.core.constant.EnumRole;
import com.turborvip.core.domain.repositories.UserRepository;
import com.turborvip.core.model.dto.Coordinates;
import com.turborvip.core.model.entity.Role;
import com.turborvip.core.service.H3Service;
import com.turborvip.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ApplicationConfig {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final H3Service h3Service;


    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            /*userService.saveRole(new Role((long) 5, EnumRole.BUSINESS, "Business"));*/

            /*userService.saveRole(new Role(null, EnumRole.ROLE_USER,"User"));
            userService.saveRole(new Role(null, EnumRole.ROLE_SUPER_ADMIN,"Supper Admin"));
            userService.saveRole(new Role(null, EnumRole.ROLE_ADMIN,"Admin"));
            userService.saveRole(new Role(null, EnumRole.MANAGER, "Manager"));*/
            //SimpleDateFormat formatter = new SimpleDateFormat(CommonConstant.FORMAT_DATE_PATTERN);
            /*Date now = new Date();

            HttpServletRequest request = null;
            User superAdmin = new User("TurborvipSuperAdmin", "turborvipSuperAdmin", "123456a", "turborvip@gmail.com", now, null, null, null, null, new HashSet<>());
            User user = new User("TurborvipUser", "turborvipUser", "123456a", "turborvip@gmail.com", now, null, null, null, null, new HashSet<>());
            User admin = new User("TurborvipAdmin", "turborvipAdmin", "123456a", "turborvip@gmail.com", now, null, null, null, null, new HashSet<>());
            User manager = new User("TurborvipManager", "turborvipManager", "123456a", "turborvip@gmail.com", now, null, null, null, null, new HashSet<>());

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

            userService.addToUser("turborvipSuperAdmin", String.valueOf(EnumRole.ROLE_SUPER_ADMIN));
            userService.addToUser("turborvipUser", String.valueOf(EnumRole.ROLE_USER));
            userService.addToUser("turborvipAdmin", String.valueOf(EnumRole.ROLE_ADMIN));
            userService.addToUser("turborvipManager", String.valueOf(EnumRole.MANAGER));*/
            //test

            double km = h3Service.calculateDistance(new Coordinates(21.053130955287447, 105.73932319679818),new Coordinates(21.042175467219348, 105.7863161542057));
            System.out.println("xxx " + km);
        };
    }

    @Bean
    public TaskScheduler taskScheduler() {
        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        return scheduler;
    }
}

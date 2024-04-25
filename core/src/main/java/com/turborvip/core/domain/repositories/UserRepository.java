package com.turborvip.core.domain.repositories;

import com.turborvip.core.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findFirstByUsernameOrEmailOrPhone(String username, String email, String phone);

    Optional<User> findFirstByEmailOrPhone(String email, String phone);

    Optional<User> findByPhone(String phone);

    Optional<User> findByEmailAndPhoneAndIdNot(String email, String phone, long id);

}

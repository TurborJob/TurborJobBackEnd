package com.turborvip.core.domain.repositories;

import com.turborvip.core.constant.EnumRole;
import com.turborvip.core.model.entity.User;
import com.turborvip.core.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUserId_Id(long id);
    List<UserRole> findByUser(User user);

    Optional<UserRole> findByUserAndRole_Code(User user, EnumRole code);

}

package com.turborvip.core.domain.repositories;

import com.turborvip.core.constant.EnumRole;
import com.turborvip.core.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRoleName(EnumRole roleName);
}

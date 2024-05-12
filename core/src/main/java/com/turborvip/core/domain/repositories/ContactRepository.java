package com.turborvip.core.domain.repositories;


import com.turborvip.core.model.entity.Contact;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Override
    @NotNull
    Optional<Contact> findById(@NotNull Long aLong);

    long countByUserNotNull();

}

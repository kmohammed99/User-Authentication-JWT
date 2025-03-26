package com.esc.security.repository;
/// //////Step 1 to create Repo

import com.esc.security.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJPARepository extends JpaRepository<UserModel, UUID> {
    boolean existsByEmail(String email);

    Optional<UserModel> findByEmail(String email);
}

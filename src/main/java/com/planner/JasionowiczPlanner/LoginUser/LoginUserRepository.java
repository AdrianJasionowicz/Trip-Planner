package com.planner.JasionowiczPlanner.LoginUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {
    LoginUser findByEmail(String email);
    Optional<LoginUser> findByUsername(String username);


    boolean existsByUsername(String username);
}

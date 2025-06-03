package com.planner.JasionowiczPlanner.LoginUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {
    LoginUser findByEmail(String email);
    Optional<LoginUser> findByUsername(String username);
    @Query("SELECT u FROM LoginUser u LEFT JOIN FETCH u.trips WHERE u.username = :username")
    Optional<LoginUser> findByUsernameFetchTrips(@Param("username") String username);


    boolean existsByUsername(String username);

    LoginUser getByUsername(String username);
}

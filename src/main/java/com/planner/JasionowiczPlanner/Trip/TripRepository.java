package com.planner.JasionowiczPlanner.Trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    public Optional<List<Trip>> findByLoginUser_Id(Long userId);


}

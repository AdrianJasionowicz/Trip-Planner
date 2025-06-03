package com.planner.JasionowiczPlanner.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.trip.loginUser.id = :userId")
    List<Task> findAllByTrip_LoginUserId(@Param("userId") Long userId);
    List<Task> findByTrip_LoginUser_IdAndDone(Long userId, Boolean done);
    List<Task> findAllByTrip_LoginUser_Id(Long userId);



    List<Task> findByTrip_Id(Long tripId);


}


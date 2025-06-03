package com.planner.JasionowiczPlanner.Trip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.planner.JasionowiczPlanner.LoginUser.LoginUser;
import com.planner.JasionowiczPlanner.LoginUser.LoginUserDTO;
import com.planner.JasionowiczPlanner.Place.Place;
import com.planner.JasionowiczPlanner.Place.PlaceDTO;
import com.planner.JasionowiczPlanner.Reminder.Reminder;
import com.planner.JasionowiczPlanner.Reminder.ReminderDTO;
import com.planner.JasionowiczPlanner.Task.Task;
import com.planner.JasionowiczPlanner.Task.TaskDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class TripDTO {
    private Long id;
    private String title;
    private String description;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private String imageCoverUrl;
    private String country;
    private String city;
    private TripStatus status;
    @JsonIgnore
    private List<PlaceDTO> places;
    @JsonIgnore
    private List<TaskDTO> tasks;
    @JsonIgnore
    private List<ReminderDTO> reminders;
    @JsonIgnore
    private LoginUserDTO loginUser;


}

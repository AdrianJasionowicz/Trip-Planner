package com.planner.JasionowiczPlanner.Trip;

import com.planner.JasionowiczPlanner.LoginUser.LoginUser;
import com.planner.JasionowiczPlanner.Place.Place;
import com.planner.JasionowiczPlanner.Reminder.Reminder;
import com.planner.JasionowiczPlanner.Task.Task;
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
    private List<Place> places;
    private List<Task> tasks;
    private List<Reminder> reminders;
    private LoginUser loginUser;


}

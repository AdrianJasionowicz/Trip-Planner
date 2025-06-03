package com.planner.JasionowiczPlanner.Reminder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.planner.JasionowiczPlanner.Place.Place;
import com.planner.JasionowiczPlanner.Place.PlaceDTO;
import com.planner.JasionowiczPlanner.Task.Task;
import com.planner.JasionowiczPlanner.Task.TaskDTO;
import com.planner.JasionowiczPlanner.Trip.Trip;
import com.planner.JasionowiczPlanner.Trip.TripDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReminderDTO {

    private Long id;
    private String title;
    private String description;
    @NotNull(message = "remindAt is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate remindAt;
    @NotNull(message = "Reminder date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    private String method;
    private Boolean send;
    private Long tripId;
    private Long taskId;
    private Long placeId;



}
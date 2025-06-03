package com.planner.JasionowiczPlanner.Task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.planner.JasionowiczPlanner.Trip.Trip;
import com.planner.JasionowiczPlanner.Trip.TripDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskDTO {


    private Long id;
    private String name;
    private String description;
    private Boolean done;

    private LocalDate dueDate;
    private LocalDate createdAt;
    private List<String> checklist;
    private Long tripId;

}




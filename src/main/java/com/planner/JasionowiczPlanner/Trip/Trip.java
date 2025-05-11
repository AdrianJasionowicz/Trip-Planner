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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Trip {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Enumerated(EnumType.STRING)
    private TripStatus status;
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Place> places;
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reminder> reminders;
    @ManyToOne
    private LoginUser loginUser;
    @PrePersist
    protected void onCreate() {
    this.createdAt = LocalDateTime.now();
}





}

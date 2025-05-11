package com.planner.JasionowiczPlanner.Place;


import com.planner.JasionowiczPlanner.Trip.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String googleMapsUrl;
    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
    private LocalDateTime createdAt;
    private String imageUrl;
    private LocalDateTime visitDateTimeFrom;
    private LocalDateTime visitDateTimeTo;

    private String notes;
    @ElementCollection
    private List<String> tags;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


    public Duration timeUntilTrip() {
        return Duration.between(LocalDateTime.now(), this.visitDateTimeFrom);
    }
}

package com.planner.JasionowiczPlanner.Place;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.planner.JasionowiczPlanner.Trip.Trip;
import com.planner.JasionowiczPlanner.Trip.TripDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class PlaceDTO {

    private Long id;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String googleMapsUrl;
    private LocalDate createdAt;
    private String imageUrl;
    private LocalDate visitDateTimeFrom;
    private LocalDate visitDateTimeTo;
    private String notes;
    private List<String> tags;
    private Long tripId;
}

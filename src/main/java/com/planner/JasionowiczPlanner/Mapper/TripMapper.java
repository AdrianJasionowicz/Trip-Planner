package com.planner.JasionowiczPlanner.Mapper;

import com.planner.JasionowiczPlanner.Trip.Trip;
import com.planner.JasionowiczPlanner.Trip.TripDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TripMapper {
    TripDTO toDto(Trip trip);
    Trip toTrip(TripDTO tripDTO);
}

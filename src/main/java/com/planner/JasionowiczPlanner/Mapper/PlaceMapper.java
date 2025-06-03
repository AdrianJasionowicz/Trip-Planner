package com.planner.JasionowiczPlanner.Mapper;

import com.planner.JasionowiczPlanner.Place.Place;
import com.planner.JasionowiczPlanner.Place.PlaceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    @Mapping(source = "trip.id", target = "tripId")

    PlaceDTO toDto(Place place);
    @Mapping(target = "trip", ignore = true)
    Place toEntity(PlaceDTO placeDTO);
    default List<Place> toEntity(List<PlaceDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .toList();
    }

}

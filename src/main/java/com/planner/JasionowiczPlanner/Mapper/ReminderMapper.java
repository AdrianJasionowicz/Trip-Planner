package com.planner.JasionowiczPlanner.Mapper;

import com.planner.JasionowiczPlanner.Place.Place;
import com.planner.JasionowiczPlanner.Reminder.Reminder;
import com.planner.JasionowiczPlanner.Reminder.ReminderDTO;
import com.planner.JasionowiczPlanner.Task.Task;
import com.planner.JasionowiczPlanner.Trip.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReminderMapper {
    @Mapping(source = "trip.id", target = "tripId")
    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "place.id", target = "placeId")
    ReminderDTO toDto(Reminder reminder);
    @Mapping(source = "tripId", target = "trip", qualifiedByName = "mapTripId")
    @Mapping(source = "taskId", target = "task", qualifiedByName = "mapTaskId")
    @Mapping(source = "placeId", target = "place", qualifiedByName = "mapPlaceId")
    Reminder fromDto(ReminderDTO reminderDTO);
    default List<Reminder> toEntity(List<ReminderDTO> dtos) {
        return dtos.stream()
                .map(this::fromDto)
                .toList();
    }
    @Named("mapTripId")
    default Trip mapTripId(Long id) {
        if (id == null) return null;
        Trip trip = new Trip();
        trip.setId(id);
        return trip;
    }

    @Named("mapTaskId")
    default Task mapTaskId(Long id) {
        if (id == null) return null;
        Task task = new Task();
        task.setId(id);
        return task;
    }

    @Named("mapPlaceId")
    default Place mapPlaceId(Long id) {
        if (id == null) return null;
        Place place = new Place();
        place.setId(id);
        return place;
    }

}

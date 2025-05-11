package com.planner.JasionowiczPlanner.Trip;

import com.planner.JasionowiczPlanner.Mapper.TripMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;


    public TripService(TripRepository tripRepository, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
    }

    public List<TripDTO> getUserTrips(Long userId) {
        List<Trip> trips = tripRepository.findByLoginUser_Id(userId);
        List<TripDTO> dtos = new ArrayList<>();
        for (Trip trip : trips) {
            dtos.add(tripMapper.toDto(trip));
        }
        return dtos;

    }

    public void addTrip(TripDTO trip) {
        Trip trip1 = new Trip();
        trip1 = tripMapper.toTrip(trip);
        tripRepository.save(trip1);
    }

    public void updateTrip(Long tripId, TripDTO trip) {
        Trip trip1 = tripRepository.getReferenceById(tripId);
        if (trip.getCity() != null) {
            trip1.setCity(trip.getCity());
        }
        if (trip.getDescription() != null) {
            trip1.setDescription(trip.getDescription());
        }
        if (trip.getTitle() != null) {
            trip1.setTitle(trip.getTitle());
        }
        if (trip.getStartDate() != null) {
            trip1.setStartDate(trip.getStartDate());
        }
        if (trip.getEndDate() != null) {
            trip1.setEndDate(trip.getEndDate());
        }
        if (trip.getStatus() != null) {
            trip1.setStatus(trip.getStatus());
        }
        if (trip.getCreatedAt() != null) {
            trip1.setCreatedAt(trip.getCreatedAt());
        }
        if (trip.getImageCoverUrl() != null) {
            trip1.setImageCoverUrl(trip.getImageCoverUrl());
        }
        if (trip.getCountry() != null) {
            trip1.setCountry(trip.getCountry());
        }
        if (trip.getPlaces() != null) {
            trip1.setPlaces(trip.getPlaces());
        }
        if (trip.getReminders() != null) {
            trip1.setReminders(trip.getReminders());
        }
        if (trip.getTasks() != null) {
            trip1.setTasks(trip.getTasks());
        }
        tripRepository.save(trip1);
    }

    public void deleteTrip(Long tripId) {
        tripRepository.deleteById(tripId);
    }

    public TripDTO getTrip(Long tripId) {
        Trip trip = tripRepository.getById(tripId);
        TripDTO tripDTO = new TripDTO();
        tripDTO = tripMapper.toDto(trip);
        return tripDTO;
    }
}

package com.planner.JasionowiczPlanner.Trip;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    private final TripRepository tripRepository;


    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<TripDTO> getUserTrips(Long userId) {
        Optional<List<Trip>> optTrip = tripRepository.findByLoginUser_Id(userId);
        if (!optTrip.isPresent()) {
            return null;
        }
        List<Trip> trips = optTrip.get();
        List<TripDTO> dtos = new ArrayList<>();
        for (Trip trip : trips) {
           dtos.add(convertTripToDTO(trip));
        }
        return dtos;

    }


    public TripDTO convertTripToDTO(Trip trip) {
        TripDTO tripDTO = new TripDTO();
        tripDTO.setId(trip.getId());
        tripDTO.setCity(trip.getCity());
        tripDTO.setDescription(trip.getDescription());
        tripDTO.setTitle(trip.getTitle());
        tripDTO.setStartDate(trip.getStartDate());
        tripDTO.setEndDate(trip.getEndDate());
        tripDTO.setStatus(trip.getStatus());
        tripDTO.setCreatedAt(trip.getCreatedAt());
        tripDTO.setImageCoverUrl(trip.getImageCoverUrl());
        tripDTO.setCountry(trip.getCountry());
        tripDTO.setPlaces(trip.getPlaces());
        tripDTO.setReminders(trip.getReminders());
        tripDTO.setTasks(trip.getTasks());
        return tripDTO;
    }

    public Trip convertDTOToTrip(TripDTO tripDTO) {
        Trip trip = new Trip();
        trip.setId(tripDTO.getId());
        trip.setCity(tripDTO.getCity());
        trip.setDescription(tripDTO.getDescription());
        trip.setTitle(tripDTO.getTitle());
        trip.setStartDate(tripDTO.getStartDate());
        trip.setEndDate(tripDTO.getEndDate());
        trip.setStatus(tripDTO.getStatus());
        trip.setCreatedAt(tripDTO.getCreatedAt());
        trip.setImageCoverUrl(tripDTO.getImageCoverUrl());
        trip.setCountry(tripDTO.getCountry());
        trip.setPlaces(tripDTO.getPlaces());
        trip.setReminders(tripDTO.getReminders());
        trip.setTasks(tripDTO.getTasks());
        return trip;
    }


    public void addTrip(TripDTO trip) {
    Trip trip1 = new Trip();
    trip1 = convertDTOToTrip(trip);
    tripRepository.save(trip1);
    }

    public void updateTrip(Long tripId, TripDTO trip) {
       Trip trip1 =  tripRepository.getReferenceById(tripId);
       if (trip1.getCity() != null) {
           trip1.setCity(trip.getCity());
       }
       if (trip1.getDescription() != null) {
           trip1.setDescription(trip.getDescription());
       }
       if (trip1.getTitle() != null) {
           trip1.setTitle(trip.getTitle());
       }
       if (trip1.getStartDate() != null) {
           trip1.setStartDate(trip.getStartDate());
       }
       if (trip1.getEndDate() != null) {
           trip1.setEndDate(trip.getEndDate());
       }
       if (trip1.getStatus() != null) {
           trip1.setStatus(trip.getStatus());
       }
       if (trip1.getCreatedAt() != null) {
           trip1.setCreatedAt(trip.getCreatedAt());
       }
       if (trip1.getImageCoverUrl() != null) {
           trip1.setImageCoverUrl(trip.getImageCoverUrl());
       }
       if (trip1.getCountry() != null) {
           trip1.setCountry(trip.getCountry());
       }
       if (trip1.getPlaces() != null) {
           trip1.setPlaces(trip.getPlaces());
       }
       if (trip1.getReminders() != null) {
           trip1.setReminders(trip.getReminders());
       }
       if (trip1.getTasks() != null) {
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
       tripDTO = convertTripToDTO(trip);
        return tripDTO ;
    }
}

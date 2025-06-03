package com.planner.JasionowiczPlanner.Place;

import com.planner.JasionowiczPlanner.Mapper.PlaceMapper;
import com.planner.JasionowiczPlanner.Mapper.TripMapper;
import com.planner.JasionowiczPlanner.Trip.Trip;
import com.planner.JasionowiczPlanner.Trip.TripRepository;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaceService {
    private PlaceRepository placeRepository;
    private PlaceMapper placeMapper;
    private TripMapper tripMapper;
    private TripRepository tripRepository;

    public PlaceService(PlaceRepository placeRepository, PlaceMapper placeMapper, TripMapper tripMapper, TripRepository tripRepository) {
        this.placeRepository = placeRepository;
        this.placeMapper = placeMapper;
        this.tripMapper = tripMapper;
        this.tripRepository = tripRepository;
    }

    public List<PlaceDTO> getAllPlaces() {
        List<Place> places = placeRepository.findAll();
        return places
                .stream()
                .map(placeMapper::toDto)
                .collect(Collectors.toList());
    }

    public PlaceDTO getPlaceById(long id) {
        PlaceDTO placeDTO = placeMapper.toDto(placeRepository.getReferenceById(id));
        return placeDTO;
    }

    public Place createNewPlace(PlaceDTO dto) {
        if (dto.getTripId() == null) {
            throw new IllegalArgumentException("Trip ID is required");
        }

        Trip trip = tripRepository.findById(dto.getTripId())
                .orElseThrow(() -> new IllegalArgumentException("Trip not found for ID: " + dto.getTripId()));

        Place place = new Place();
        place.setName(dto.getName());
        place.setDescription(dto.getDescription());
        place.setLatitude(dto.getLatitude());
        place.setLongitude(dto.getLongitude());
        place.setGoogleMapsUrl(dto.getGoogleMapsUrl());
        place.setCreatedAt(dto.getCreatedAt());
        place.setImageUrl(dto.getImageUrl());
        place.setVisitDateTimeFrom(dto.getVisitDateTimeFrom());
        place.setVisitDateTimeTo(dto.getVisitDateTimeTo());
        place.setNotes(dto.getNotes());
        place.setTags(dto.getTags());
        place.setTrip(trip);

        return placeRepository.save(place);
    }



    public void patchPlace(long id, PlaceDTO placeDTO) {
        Place place = placeRepository.getReferenceById(id);
        if (placeDTO.getName() != null) {
            place.setName(placeDTO.getName());
        }
        if (placeDTO.getDescription() != null) {
            place.setDescription(placeDTO.getDescription());
        }
        if (placeDTO.getLatitude() != null) {
            place.setLatitude(placeDTO.getLatitude());
        }
        if (placeDTO.getLongitude() != null) {
            place.setLongitude(placeDTO.getLongitude());
        }
        if (placeDTO.getGoogleMapsUrl() != null) {
            place.setGoogleMapsUrl(placeDTO.getGoogleMapsUrl());
        }
        if (placeDTO.getTripId() != null) {
            Trip trip = tripRepository.getReferenceById(placeDTO.getTripId());
            place.setTrip(trip);
        }

        if (placeDTO.getCreatedAt() != null) {
            place.setCreatedAt(placeDTO.getCreatedAt());
        }
        if (placeDTO.getImageUrl() != null) {
            place.setImageUrl(placeDTO.getImageUrl());
        }
        if (placeDTO.getVisitDateTimeFrom() != null) {
            place.setVisitDateTimeFrom(placeDTO.getVisitDateTimeFrom());
        }
        if (placeDTO.getVisitDateTimeTo() != null) {
            place.setVisitDateTimeTo(placeDTO.getVisitDateTimeTo());
        }
        if (placeDTO.getNotes() != null) {
            place.setNotes(placeDTO.getNotes());
        }
        if (placeDTO.getTags() != null) {
            place.setTags(placeDTO.getTags());
        }
        placeRepository.save(place);


    }

    public void updatePlace(long id, PlaceDTO placeDTO) {
        Place place = placeRepository.getReferenceById(id);

        Optional.ofNullable(placeDTO.getName()).ifPresent(place::setName);
        Optional.ofNullable(placeDTO.getDescription()).ifPresent(place::setDescription);
        Optional.ofNullable(placeDTO.getLatitude()).ifPresent(place::setLatitude);
        Optional.ofNullable(placeDTO.getLongitude()).ifPresent(place::setLongitude);
        Optional.ofNullable(placeDTO.getGoogleMapsUrl()).ifPresent(place::setGoogleMapsUrl);

        Optional.ofNullable(placeDTO.getTripId()).ifPresent(tripDto -> {
            Trip trip = tripRepository.getReferenceById(tripDto);
            place.setTrip(trip);
        });
        Optional.ofNullable(placeDTO.getCreatedAt()).ifPresent(place::setCreatedAt);
        Optional.ofNullable(placeDTO.getImageUrl()).ifPresent(place::setImageUrl);
        Optional.ofNullable(placeDTO.getVisitDateTimeFrom()).ifPresent(place::setVisitDateTimeFrom);
        Optional.ofNullable(placeDTO.getVisitDateTimeTo()).ifPresent(place::setVisitDateTimeTo);
        Optional.ofNullable(placeDTO.getNotes()).ifPresent(place::setNotes);
        Optional.ofNullable(placeDTO.getTags()).ifPresent(place::setTags);

        placeRepository.save(place);
    }

    public void deletePlaceById(long id) {
        placeRepository.deleteById(id);
    }

    public List<PlaceDTO> getUserPlaces(long userId) {
        List<Place> placesList = placeRepository.findByTrip_LoginUser_Id(userId);
        return placesList
                .stream()
                .map(placeMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PlaceDTO> getAllPlacesByTripId(long tripId) {
        List<Place> placesList = placeRepository.findByTrip_Id(tripId);
        return placesList
                .stream()
                .map(placeMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PlaceDTO> getPlacesNear(double lat, double lon, double radiusKm) {
        return placeRepository.findPlacesNear(lat, lon, radiusKm)
                .stream()
                .map(placeMapper::toDto)
                .toList();
    }
    public List<PlaceDTO> getPlacesByTripId(Long tripId) {
        List<Place> places = placeRepository.findAllByTripId(tripId);
        return places.stream().map(placeMapper::toDto).toList();
    }

}



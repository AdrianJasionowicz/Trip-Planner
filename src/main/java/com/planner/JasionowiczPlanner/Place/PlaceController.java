package com.planner.JasionowiczPlanner.Place;

import com.planner.JasionowiczPlanner.Trip.TripDTO;
import com.planner.JasionowiczPlanner.Trip.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlaceController {
    private final TripService tripService;
    private PlaceService placeService;

    public PlaceController(PlaceService placeService, TripService tripService) {
        this.placeService = placeService;
        this.tripService = tripService;
    }

    @GetMapping("/places/all")
    public ResponseEntity<List<PlaceDTO>> getAllPlaces() {
        List<PlaceDTO> placeDTO = placeService.getAllPlaces();
        return placeDTO.isEmpty()
                ? ResponseEntity.noContent().header("Error").build()
                : ResponseEntity.ok(placeDTO);
    }

    @GetMapping("/places/{id}")
    public ResponseEntity<PlaceDTO> getPlaceById(@PathVariable long id) {
        PlaceDTO placeDTO = placeService.getPlaceById(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Place has been found")
                .body(placeDTO);
    }

    @PostMapping("/places")
    public ResponseEntity<PlaceDTO> createPlace(@RequestBody PlaceDTO placeDTO) {
        placeService.createNewPlace(placeDTO);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Place has been created")
                .body(placeDTO);
    }

    @PutMapping("/places/{id}")
    public ResponseEntity<PlaceDTO> updatePlace(@PathVariable long id, @RequestBody PlaceDTO placeDTO) {
        placeService.updatePlace(id, placeDTO);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Place has been updated")
                .body(placeDTO);
    }

    @PatchMapping("/places/{id}")
    public ResponseEntity<PlaceDTO> patchPlace(@PathVariable long id, @RequestBody PlaceDTO placeDTO) {
        placeService.patchPlace(id, placeDTO);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Place has been patched")
                .body(placeDTO);
    }

    @DeleteMapping("/places/{id}")
    public ResponseEntity<String> deletePlace(@PathVariable long id) {
        placeService.deletePlaceById(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Place deleted")
                .body("Place deleted");
    }

    @GetMapping("places/user/{userId}")
    public ResponseEntity<List<PlaceDTO>> getUserPlaces(@PathVariable long userId) {
        List<PlaceDTO> placeDTOList = placeService.getUserPlaces(userId);
        return placeDTOList.isEmpty()
                ? ResponseEntity.noContent().header("Error").build()
                : ResponseEntity.ok(placeDTOList);
    }

    @GetMapping("/places/trip/{tripId}")
    public ResponseEntity<List<PlaceDTO>> getTripPlaces(@PathVariable long tripId) {
        List<PlaceDTO> placeDTOList = placeService.getAllPlacesByTripId(tripId);
        return placeDTOList.isEmpty()
                ? ResponseEntity.noContent().header("Error").build()
                : ResponseEntity.ok(placeDTOList);
    }

    @GetMapping("/places/near")
    public ResponseEntity<List<PlaceDTO>> getPlacesNear(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam double radius) {
        return ResponseEntity.ok(placeService.getPlacesNear(lat, lon, radius));
    }

    @GetMapping("/trip/{id}/places")
    public List<PlaceDTO> getPlacesByTripId(@PathVariable Long id) {
        return placeService.getPlacesByTripId(id);
    }


}

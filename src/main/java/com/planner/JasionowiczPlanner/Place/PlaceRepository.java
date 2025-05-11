package com.planner.JasionowiczPlanner.Place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByTrip_LoginUser_Id(Long userId);
    List<Place> findByTrip_Id(Long tripId);
    @Query(value = """
    SELECT * FROM place WHERE (6371 * acos(cos(radians(:lat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:lon)) + sin(radians(:lat)) * sin(radians(latitude)))) <= :radius""", nativeQuery = true)
    List<Place> findPlacesNear(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("radius") double radiusKm);

}

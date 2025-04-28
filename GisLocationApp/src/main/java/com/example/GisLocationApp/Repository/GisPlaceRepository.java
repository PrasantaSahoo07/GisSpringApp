package com.example.GisLocationApp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.GisLocationApp.Modal.GisPlaces;

public interface GisPlaceRepository extends JpaRepository<GisPlaces,Long>{
	
	@Query(value = "SELECT * FROM place WHERE ST_DWithin(location, ST_MakePoint(:lon, :lat)::geography, :radius)", nativeQuery = true)
	List<GisPlaces> findNearby(@Param("lat") double lat, @Param("lon") double lon, @Param("radius") double radius);

	    @Query(value = "SELECT * FROM place ORDER BY location <-> ST_MakePoint(:lon, :lat)::geography LIMIT 1", nativeQuery = true)
	    GisPlaces findNearest(@Param("lat") double lat, @Param("lon") double lon);

	    @Query(value = "SELECT ST_Distance(p1.location, p2.location) FROM place p1, place p2 WHERE p1.id = :id1 AND p2.id = :id2", nativeQuery = true)
	    double calculateDistance(@Param("id1") Long id1, @Param("id2") Long id2);

}

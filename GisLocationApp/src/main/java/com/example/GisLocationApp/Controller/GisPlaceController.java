package com.example.GisLocationApp.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.GisLocationApp.DTO.GisPlaceDTO;
import com.example.GisLocationApp.Modal.GisPlaces;
import com.example.GisLocationApp.Repository.GisPlaceRepository;
import com.example.GisLocationApp.Service.GisPlaceService;

@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/api/places")
public class GisPlaceController {
	
	@Autowired
	private GisPlaceRepository gisPlaceRepository;
	@Autowired
	private GisPlaceService gisPlaceService;

	@Autowired
	private GeometryFactory geometryFactory;
	@PostMapping("/add")
	public ResponseEntity<?> addPlace(@RequestBody GisPlaceDTO dto) {
	    try {
	        if (dto == null || dto.getLatitude() == null || dto.getLongitude() == null) {
	            return ResponseEntity.badRequest().body("Latitude and Longitude must not be null");
	        }
	        Point point = geometryFactory.createPoint(new Coordinate(dto.getLongitude(), dto.getLatitude()));
	        point.setSRID(4326);

	        GisPlaces place = new GisPlaces();
	        place.setLocation(point);
	        place.setName(dto.getName());
	        place.setType(dto.getType());
	        gisPlaceRepository.save(place);
	        return ResponseEntity.ok("Place added successfully");
	    } catch (Exception e) {
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error while adding place: " + e.getMessage());
	    }
	}


	   @GetMapping("/nearby")
	    public ResponseEntity<?> findNearby(
	            @RequestParam double lat,
	            @RequestParam double lon,
	            @RequestParam double radius) {
	        try {
	            if (radius <= 0) {
	                return ResponseEntity.badRequest().body("Radius must be greater than 0");
	            }
	            List<GisPlaces> nearby = gisPlaceRepository.findNearby(lat, lon, radius * 1000); // km to meters
	          
	            List<GisPlaceDTO> gisPlaceDTOList = nearby.stream()
	                    .map(gisPlace -> gisPlaceService.convertToDTO(gisPlace))
	                    .collect(Collectors.toList());
	            
	            return ResponseEntity.ok(gisPlaceDTOList);

	        } catch (Exception e) {
	            return ResponseEntity
	                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Error fetching nearby places: " + e.getMessage());
	        }
	    }

	    // Nearest Place
	    @GetMapping("/nearest")
	    public ResponseEntity<?> findNearest(
	            @RequestParam double lat,
	            @RequestParam double lon) {
	        try {
	            GisPlaces nearest = gisPlaceRepository.findNearest(lat, lon);
	            if (nearest == null) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No place found nearby");
	            }
	            GisPlaceDTO nearestlocation = gisPlaceService.convertToDTO(nearest);
	            return ResponseEntity.ok(nearestlocation);
	        } catch (Exception e) {
	            return ResponseEntity
	                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Error finding nearest place: " + e.getMessage());
	        }
	    }

	    // Distance Between Two Given Places
	    @GetMapping("/distance")
	    public ResponseEntity<?> distance(@RequestParam Long id1, @RequestParam Long id2) {
	        try {
	            if (id1 == null || id2 == null) {
	                return ResponseEntity.badRequest().body("Both place IDs must be provided");
	            }

	            double dist = gisPlaceRepository.calculateDistance(id1, id2);
	            return ResponseEntity.ok(dist);

	        } catch (Exception e) {
	            return ResponseEntity
	                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Error calculating distance: " + e.getMessage());
	        }
	    }
}

package com.example.GisLocationApp.Service;

import org.springframework.stereotype.Service;

import com.example.GisLocationApp.DTO.GisPlaceDTO;
import com.example.GisLocationApp.Modal.GisPlaces;

@Service
public class GisPlaceService {
	 public GisPlaceDTO convertToDTO(GisPlaces gisPlace) {
	        // Extract latitude and longitude from the Point object
	        Double latitude = gisPlace.getLocation().getY();  // Latitude is stored in the Y coordinate
	        Double longitude = gisPlace.getLocation().getX(); // Longitude is stored in the X coordinate

	        // Return the DTO
	        return new GisPlaceDTO(gisPlace.getId(), gisPlace.getName(), gisPlace.getType(), latitude, longitude);
	    }
}

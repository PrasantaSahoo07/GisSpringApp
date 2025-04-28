package com.example.GisLocationApp.Modal;


import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString()
@Table(name="place")
public class GisPlaces {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String name;

	    private String type;
	   
	    @Column(columnDefinition = "geometry(Point,4326)")
	    private Point location;
	    
	    @JsonProperty("latitude")
	    public double getLatitude() {
	        return location != null ? location.getY() : 0.0; // Extract latitude from Point
	    }

	    @JsonProperty("longitude")
	    public double getLongitude() {
	        return location != null ? location.getX() : 0.0; // Extract longitude from Point
	    }
}

package com.example.GisLocationApp.DTO;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GisPlaceDTO {
	private Long id;
    private String name;
    private String type;
    private Double latitude;
    private Double longitude;

}

package com.api.parkingcontrol.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpotRequestBody {
	
	@NotBlank
	private String parkingSpotNumber;
	
	@NotBlank
	@Size(max = 7)
	private String licensePlateCar;
	
	@NotBlank
	private String brandCar;
	
	@NotBlank
	private String modelCar;
	
	@NotBlank
	private String colorCar;
	
	@NotEmpty(message = "The responsible name cannot be empty")
	private String responsibleName;
	
	@NotBlank
	private String apartment;
	
	@NotBlank
	private String block;
}

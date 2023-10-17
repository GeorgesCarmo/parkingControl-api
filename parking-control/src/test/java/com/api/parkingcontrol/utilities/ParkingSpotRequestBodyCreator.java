package com.api.parkingcontrol.utilities;

import com.api.parkingcontrol.requests.ParkingSpotRequestBody;

public class ParkingSpotRequestBodyCreator {

	public static ParkingSpotRequestBody createParkingSpotRequestBody() {
		return ParkingSpotRequestBody.builder()
				.parkingSpotNumber(ParkingSpotCreator.createParkingSpotToBeSaved().getParkingSpotNumber())
				.licensePlateCar(ParkingSpotCreator.createParkingSpotToBeSaved().getLicensePlateCar())
				.brandCar(ParkingSpotCreator.createParkingSpotToBeSaved().getBrandCar())
				.modelCar(ParkingSpotCreator.createParkingSpotToBeSaved().getModelCar())
				.colorCar(ParkingSpotCreator.createParkingSpotToBeSaved().getColorCar())
				.responsibleName(ParkingSpotCreator.createParkingSpotToBeSaved().getResponsibleName())
				.build();
	}
}

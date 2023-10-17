package com.api.parkingcontrol.utilities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import com.api.parkingcontrol.domain.ParkingSpot;

public class ParkingSpotCreator {

	public static ParkingSpot createParkingSpotToBeSaved() {
		return ParkingSpot.builder()
				.parkingSpotNumber("101A")
				.licensePlateCar("HDFG54")
				.brandCar("Audi A8")
				.modelCar("Sport")
				.colorCar("White")
				.registrationDate(LocalDateTime.now(ZoneId.of("UTC")))
				.responsibleName("Luana Pereira")
				.apartment("101")
				.block("A")
				.build();
	}
	
	public static ParkingSpot createValidParkingSpot() {
		return ParkingSpot.builder()
				.id(UUID.fromString("d3f8078d-f836-4ccd-9efb-56e77c0ea3d3"))
				.parkingSpotNumber("101A")
				.licensePlateCar("HDFG54")
				.brandCar("Audi A8")
				.modelCar("Sport")
				.colorCar("White")
				.registrationDate(LocalDateTime.now(ZoneId.of("UTC")))
				.responsibleName("Luana Pereira")
				.apartment("101")
				.block("A")
				.build();
	}
	
	public static ParkingSpot createValidUpdatedParkingSpot() {
		return ParkingSpot.builder()
				.id(UUID.randomUUID())
				.parkingSpotNumber("101A")
				.licensePlateCar("HDFG54")
				.brandCar("Audi A8")
				.modelCar("Sport")
				.colorCar("White")
				.registrationDate(LocalDateTime.now(ZoneId.of("UTC")))
				.responsibleName("Georges do Carmo")
				.build();
	}
}

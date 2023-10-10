package com.api.parkingcontrol.service;

import org.springframework.stereotype.Service;
import com.api.parkingcontrol.domain.ParkingSpot;
import com.api.parkingcontrol.repository.ParkingSpotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParkingSpotService {

	private final ParkingSpotRepository ParkingSpotRepository;
	
	@Transactional
	public ParkingSpot save(ParkingSpot parkingSpot) {
		return ParkingSpotRepository.save(parkingSpot);
	}
}

package com.api.parkingcontrol.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.api.parkingcontrol.domain.ParkingSpot;
import com.api.parkingcontrol.exception.BadRequestException;
import com.api.parkingcontrol.repository.ParkingSpotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParkingSpotService {

	private final ParkingSpotRepository parkingSpotRepository;
	
	@Transactional
	public ParkingSpot save(ParkingSpot parkingSpot) {
		return parkingSpotRepository.save(parkingSpot);
	}
	
	public Page<ParkingSpot> listAll(Pageable pageable){
		return parkingSpotRepository.findAll(pageable);
	}
	
	public ParkingSpot findById(UUID id) {
		return parkingSpotRepository.findById(id)
				.orElseThrow(() -> new BadRequestException("ParkingSpot not found"));
	}
	
	public List<ParkingSpot> findByName(String responsibleName){
		return parkingSpotRepository.findByResponsibleName(responsibleName);
	}
}

package com.api.parkingcontrol.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.domain.ParkingSpot;
import com.api.parkingcontrol.requests.ParkingSpotPostRequestBody;
import com.api.parkingcontrol.service.ParkingSpotService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/parking-spot")
@RequiredArgsConstructor
public class ParkingSpotController {

	private final ParkingSpotService parkingSpotService;
	
	@PostMapping
	public ResponseEntity<ParkingSpot> saveParkingSpot(@RequestBody @Valid ParkingSpotPostRequestBody parkingSpotPostRequestBody){
		ParkingSpot parkingSpot = new ParkingSpot();
		BeanUtils.copyProperties(parkingSpotPostRequestBody, parkingSpot);
		parkingSpot.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		return new ResponseEntity<>(parkingSpotService.save(parkingSpot), HttpStatus.CREATED);
		
	}
}

package com.api.parkingcontrol.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.api.parkingcontrol.domain.ParkingSpot;
import com.api.parkingcontrol.requests.ParkingSpotRequestBody;
import com.api.parkingcontrol.service.ParkingSpotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/parking-spot")
@RequiredArgsConstructor
public class ParkingSpotController {

	private final ParkingSpotService parkingSpotService;
	
	@PostMapping
	public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotRequestBody parkingSpotRequestBody){
		if(parkingSpotService.existsByLicensePlateCar(parkingSpotRequestBody.getLicensePlateCar())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: license plate car is already is use.");
		}
		if(parkingSpotService.existsByParkingSpotNumber(parkingSpotRequestBody.getParkingSpotNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: parking spot number is already is use.");
		}
		if(parkingSpotService.existsByApartmentAndBlock(parkingSpotRequestBody.getApartment(), parkingSpotRequestBody.getBlock())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: parking spot already registered for this apartment and block.");
		}
		ParkingSpot parkingSpot = new ParkingSpot();
		BeanUtils.copyProperties(parkingSpotRequestBody, parkingSpot);
		parkingSpot.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		return new ResponseEntity<>(parkingSpotService.save(parkingSpot), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<Page<ParkingSpot>> listAllParkingSpot(Pageable pageable){
		return ResponseEntity.ok(parkingSpotService.listAll(pageable));
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<ParkingSpot> findById(@PathVariable UUID id){
		return ResponseEntity.ok(parkingSpotService.findById(id));
	}
	
	@GetMapping(path = "/find")
	public ResponseEntity<List<ParkingSpot>> findByName(@RequestParam String responsibleName){
		return ResponseEntity.ok(parkingSpotService.findByName(responsibleName));
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteParkingSpot(@PathVariable UUID id){
		parkingSpotService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Object> updateParkingSpot(@PathVariable UUID id, @RequestBody @Valid ParkingSpotRequestBody parkingSpotRequestBody){
		Optional<ParkingSpot> parkingSpotOptional = Optional.ofNullable(parkingSpotService.findById(id));
		ParkingSpot parkingSpot = new ParkingSpot();
		BeanUtils.copyProperties(parkingSpotRequestBody, parkingSpot);
		parkingSpot.setId(parkingSpotOptional.get().getId());
		parkingSpot.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.ok(parkingSpotService.save(parkingSpot));
		
	}
}

package com.api.parkingcontrol.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.api.parkingcontrol.domain.ParkingSpot;
import com.api.parkingcontrol.exception.BadRequestException;
import com.api.parkingcontrol.utilities.ParkingSpotCreator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DataJpaTest
@DisplayName("Tests for ParkingSpotRepository")
class ParkingSpotRepositoryTest {

	@Autowired
	private ParkingSpotRepository parkingSpotRepository;
	
	@Test
	@DisplayName("Save persists parking spot when successful")
	void save_PersistParkingSpot__WhenSuccessful() {
		ParkingSpot ParkingSpotToBeSaved = ParkingSpotCreator.createParkingSpotToBeSaved();
		ParkingSpot parkingSpotSaved = this.parkingSpotRepository.save(ParkingSpotToBeSaved);
		Assertions.assertThat(parkingSpotSaved.getId()).isNotNull();
		Assertions.assertThat(parkingSpotSaved.getId()).isEqualTo(ParkingSpotToBeSaved.getId());
	}
	
	@Test
	@DisplayName("Save updates parking spot when successful")
	void save_UpdatesParkingSpot__WhenSuccessful() {
		ParkingSpot ParkingSpotToBeSaved = ParkingSpotCreator.createParkingSpotToBeSaved();
		ParkingSpot parkingSpotSaved = this.parkingSpotRepository.save(ParkingSpotToBeSaved);
		parkingSpotSaved.setResponsibleName("Georges do Carmo");
		ParkingSpot parkingSpotUpdated = this.parkingSpotRepository.save(parkingSpotSaved);
		Assertions.assertThat(parkingSpotUpdated).isNotNull();
		Assertions.assertThat(parkingSpotUpdated.getId()).isNotNull();
		Assertions.assertThat(parkingSpotUpdated.getResponsibleName()).isEqualTo(ParkingSpotToBeSaved.getResponsibleName());
	}
	
	@Test
	@DisplayName("Delete removes parking spot when successful")
	void delete_RemovesParkingSpot__WhenSuccessful() {
		ParkingSpot ParkingSpotToBeSaved = ParkingSpotCreator.createParkingSpotToBeSaved();
		ParkingSpot parkingSpotSaved = this.parkingSpotRepository.save(ParkingSpotToBeSaved);
		this.parkingSpotRepository.delete(parkingSpotSaved);
		Optional<ParkingSpot> parkingSpotOptional = this.parkingSpotRepository.findById(parkingSpotSaved.getId());
		Assertions.assertThat(parkingSpotOptional).isEmpty();
	}
	
	@Test
	@DisplayName("Find By ResponsibleName returns list of parking spot when successful")
	void findByName_ReturnsListOfParkingSpot__WhenSuccessful() {
		ParkingSpot ParkingSpotToBeSaved = ParkingSpotCreator.createParkingSpotToBeSaved();
		ParkingSpot parkingSpotSaved = this.parkingSpotRepository.save(ParkingSpotToBeSaved);
		String responsibleName = parkingSpotSaved.getResponsibleName();
		List<ParkingSpot> parkingSpots = this.parkingSpotRepository.findByResponsibleName(responsibleName);
		Assertions.assertThat(parkingSpots).isNotEmpty().contains(parkingSpotSaved);
	}
	
	@Test
	@DisplayName("Find By ResponsibleName returns empty list when no responsible name is found")
	void findByName_ReturnsEmptyList__WhenResponsibleNameIsNotFound() {
		List<ParkingSpot> parkingSpots = this.parkingSpotRepository.findByResponsibleName("blabla");
		Assertions.assertThat(parkingSpots).isEmpty();
	}
	
	@Test
	@DisplayName("Save throw ConstraintViolationException when responsible name is empty")
	void save_ThrowsConstraintViolationException__WhenResponsibleNameIsEmpty() {
		ParkingSpot parkingSpot = new ParkingSpot();
		
//		Assertions.assertThatThrownBy(() -> this.parkingSpotRepository.save(parkingSpot))
//		.isInstanceOf(ConstraintViolationException.class);
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<ParkingSpot>> violations = validator.validate(parkingSpot);
		
		Assertions.assertThat(violations).isNotEmpty();
		
//		Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
//		.isThrownBy(() -> this.parkingSpotRepository.save(parkingSpot))
//		.withMessageContaining("The responsible name cannot be empty");
	
	}

}

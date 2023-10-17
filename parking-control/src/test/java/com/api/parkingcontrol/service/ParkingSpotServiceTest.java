package com.api.parkingcontrol.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.api.parkingcontrol.domain.ParkingSpot;
import com.api.parkingcontrol.exception.BadRequestException;
import com.api.parkingcontrol.repository.ParkingSpotRepository;
import com.api.parkingcontrol.utilities.ParkingSpotCreator;

@ExtendWith(SpringExtension.class)
class ParkingSpotServiceTest {

	@InjectMocks
	private ParkingSpotService parkingSpotService;
	
	@Mock
	private ParkingSpotRepository parkingSpotRepositoryMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<ParkingSpot> parkingSpotPage = new PageImpl<>(List.of(ParkingSpotCreator.createValidParkingSpot()));
		
		BDDMockito.when(parkingSpotRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
		.thenReturn(parkingSpotPage);
		
		BDDMockito.when(parkingSpotRepositoryMock.findById(ArgumentMatchers.any()))
		.thenReturn(Optional.of(ParkingSpotCreator.createValidParkingSpot()));
		
		BDDMockito.when(parkingSpotRepositoryMock.findByResponsibleName(ArgumentMatchers.anyString()))
		.thenReturn(List.of(ParkingSpotCreator.createValidParkingSpot()));
		
		BDDMockito.when(parkingSpotRepositoryMock.save(ArgumentMatchers.any(ParkingSpot.class)))
		.thenReturn(ParkingSpotCreator.createValidParkingSpot());
		
		BDDMockito.when(parkingSpotRepositoryMock.save(ArgumentMatchers.any(ParkingSpot.class)))
		.thenReturn(ParkingSpotCreator.createValidUpdatedParkingSpot());
		
	}
	
	@Test
	@DisplayName("save returns parking spot when successful")
	void save_ReturnsParkingSpot_WhenSuccessful() {
		Object parkingSpot = parkingSpotService.save(ParkingSpotCreator.createParkingSpotToBeSaved());
		Assertions.assertThat(parkingSpot).isNotNull().isEqualTo(parkingSpot);
	}
	
	@Test
	@DisplayName("listAll returns list of parking spot inside page object when successful")
	void listAll_ReturnsListOfParkingSpotInsidePageObject_WhenSuccessful() {
		String expectedResponsibleName = ParkingSpotCreator.createValidParkingSpot().getResponsibleName();
		Page<ParkingSpot> parkingSpotPage = parkingSpotService.listAll(PageRequest.of(1, 1));
		Assertions.assertThat(parkingSpotPage).isNotNull();
		Assertions.assertThat(parkingSpotPage.toList()).isNotEmpty().hasSize(1);
		Assertions.assertThat(parkingSpotPage.toList().get(0).getResponsibleName()).isEqualTo(expectedResponsibleName);
	}
	
	@Test
	@DisplayName("findById returns parking spot when successful")
	void findById_ReturnsParking_WhenSuccessful() {
		UUID expectedId = ParkingSpotCreator.createValidParkingSpot().getId();
		ParkingSpot parkingSpot = parkingSpotService.findById(UUID.fromString("d3f8078d-f836-4ccd-9efb-56e77c0ea3d3"));
		Assertions.assertThat(parkingSpot).isNotNull();
		Assertions.assertThat(parkingSpot.getId()).isNotNull().isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("findById throws BadRequestException when parking spot is not found")
	void findById_ThrowsBadRequestException_WhenParkingSpotIsNotFound() {
		BDDMockito.when(parkingSpotRepositoryMock.findById(ArgumentMatchers.any()))
		.thenReturn(Optional.empty());
		
		Assertions.assertThatExceptionOfType(BadRequestException.class)
		.isThrownBy(() -> parkingSpotService.findById(UUID.randomUUID()));
	}

	@Test
	@DisplayName("findByName returns list of parking spot when successful")
	void findByName_ReturnsListOfParkingSpot_WhenSuccessful() {
		String expectedResponsibleName = ParkingSpotCreator.createValidParkingSpot().getResponsibleName();
		List<ParkingSpot> parkingSpot = parkingSpotService.findByName("lala");
		Assertions.assertThat(parkingSpot).isNotNull().isNotEmpty().hasSize(1);
		Assertions.assertThat(parkingSpot.get(0).getResponsibleName()).isEqualTo(expectedResponsibleName);
	}
	
	@Test
	@DisplayName("findByName returns an empty list of parking spot when parking spot is not found")
	void findByName_ReturnsEmptyListOfParkingSpot_WhenParkingSpotIsNotFound() {
		BDDMockito.when(parkingSpotService.findByName(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
		List<ParkingSpot> parkingSpot = parkingSpotRepositoryMock.findByResponsibleName("lala");
		Assertions.assertThat(parkingSpot).isNotNull().isEmpty();
	}

	@Test
	@DisplayName("delete removes parking spot when successful")
	void delete_RemovesParkingSpot_WhenSuccessful() {
		Assertions.assertThatCode(() -> parkingSpotService.delete(UUID.randomUUID())).doesNotThrowAnyException();
	}
}

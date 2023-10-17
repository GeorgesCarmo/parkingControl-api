package com.api.parkingcontrol.controller;

import java.util.Collections;
import java.util.List;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.api.parkingcontrol.domain.ParkingSpot;
import com.api.parkingcontrol.service.ParkingSpotService;
import com.api.parkingcontrol.utilities.ParkingSpotCreator;
import com.api.parkingcontrol.utilities.ParkingSpotRequestBodyCreator;

@ExtendWith(SpringExtension.class)
class ParkingSpotControllerTest {

	@InjectMocks
	private ParkingSpotController parkingSpotController;
	
	@Mock
	private ParkingSpotService parkingSpotService;
	
	@BeforeEach
	void setUp() {
		PageImpl<ParkingSpot> parkingSpotPage = new PageImpl<>(List.of(ParkingSpotCreator.createValidParkingSpot()));
		
		BDDMockito.when(parkingSpotService.listAll(ArgumentMatchers.any()))
		.thenReturn(parkingSpotPage);
		
		BDDMockito.when(parkingSpotService.findById(ArgumentMatchers.any()))
		.thenReturn(ParkingSpotCreator.createValidParkingSpot());
		
		BDDMockito.when(parkingSpotService.findByName(ArgumentMatchers.anyString()))
		.thenReturn(List.of(ParkingSpotCreator.createValidParkingSpot()));
		
		BDDMockito.when(parkingSpotService.save(ArgumentMatchers.any(ParkingSpot.class)))
		.thenReturn(ParkingSpotCreator.createValidParkingSpot());
		
		BDDMockito.when(parkingSpotService.save(ArgumentMatchers.any(ParkingSpot.class)))
		.thenReturn(ParkingSpotCreator.createValidUpdatedParkingSpot());
		
	}
	
	@Test
	@DisplayName("save returns parking spot when successful")
	void save_ReturnsParkingSpot_WhenSuccessful() {
		Object parkingSpot = parkingSpotController.saveParkingSpot(ParkingSpotRequestBodyCreator.createParkingSpotRequestBody()).getBody();
		Assertions.assertThat(parkingSpot).isNotNull().isEqualTo(parkingSpot);
	}
	
	@Test
	@DisplayName("listAllParkingSpot returns list of parking spot inside page object when successful")
	void listAllParkingSpot_ReturnsListOfParkingSpotInsidePageObject_WhenSuccessful() {
		String expectedResponsibleName = ParkingSpotCreator.createValidParkingSpot().getResponsibleName();
		Page<ParkingSpot> parkingSpotPage = parkingSpotController.listAllParkingSpot(null).getBody();
		Assertions.assertThat(parkingSpotPage).isNotNull();
		Assertions.assertThat(parkingSpotPage.toList()).isNotEmpty().hasSize(1);
		Assertions.assertThat(parkingSpotPage.toList().get(0).getResponsibleName()).isEqualTo(expectedResponsibleName);
	}
	
	@Test
	@DisplayName("findById returns parking spot when successful")
	void findById_ReturnsParking_WhenSuccessful() {
		UUID expectedId = ParkingSpotCreator.createValidParkingSpot().getId();
		ParkingSpot parkingSpot = parkingSpotController.findById(UUID.fromString("d3f8078d-f836-4ccd-9efb-56e77c0ea3d3")).getBody();
		Assertions.assertThat(parkingSpot).isNotNull();
		Assertions.assertThat(parkingSpot.getId()).isNotNull().isEqualTo(expectedId);
	}

	@Test
	@DisplayName("findByName returns list of parking spot when successful")
	void findByName_ReturnsListOfParkingSpot_WhenSuccessful() {
		String expectedResponsibleName = ParkingSpotCreator.createValidParkingSpot().getResponsibleName();
		List<ParkingSpot> parkingSpot = parkingSpotController.findByName("lala").getBody();
		Assertions.assertThat(parkingSpot).isNotNull().isNotEmpty().hasSize(1);
		Assertions.assertThat(parkingSpot.get(0).getResponsibleName()).isEqualTo(expectedResponsibleName);
	}
	
	@Test
	@DisplayName("findByName returns an empty list of parking spot when parking spot is not found")
	void findByName_ReturnsEmptyListOfParkingSpot_WhenParkingSpotIsNotFound() {
		BDDMockito.when(parkingSpotService.findByName(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
		List<ParkingSpot> parkingSpot = parkingSpotController.findByName("lala").getBody();
		Assertions.assertThat(parkingSpot).isNotNull().isEmpty();
	}
	
	@Test
	@DisplayName("update updates parking spot when successful")
	void update_UpdatesParkingSpot_WhenSuccessful() {
		Assertions.assertThatCode(() -> parkingSpotController.updateParkingSpot(UUID.randomUUID(),
		ParkingSpotRequestBodyCreator.createParkingSpotRequestBody())).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("delete removes parking spot when successful")
	void delete_RemovesParkingSpot_WhenSuccessful() {
		Assertions.assertThatCode(() -> parkingSpotController.deleteParkingSpot(UUID.randomUUID())).doesNotThrowAnyException();
	}
}

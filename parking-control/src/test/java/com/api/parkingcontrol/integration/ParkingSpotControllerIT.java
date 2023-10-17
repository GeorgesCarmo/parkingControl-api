package com.api.parkingcontrol.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;

import com.api.parkingcontrol.domain.ParkingSpot;
import com.api.parkingcontrol.repository.ParkingSpotRepository;
import com.api.parkingcontrol.utilities.ParkingSpotCreator;
import com.api.parkingcontrol.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ParkingSpotControllerIT {
	
	@Autowired
	public TestRestTemplate testRestTemplate;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private ParkingSpotRepository parkingSpotRepository;
	
	@Test
	@DisplayName("listAllParkingSpot returns list of parking spot inside page object when successful")
	void listAllParkingSpot_ReturnsListOfParkingSpotInsidePageObject_WhenSuccessful() {
		ParkingSpot savedParkingSpot = parkingSpotRepository.save(ParkingSpotCreator.createParkingSpotToBeSaved());
		String expectedName = savedParkingSpot.getResponsibleName();
		PageableResponse<ParkingSpot> parkingSpotPage = testRestTemplate.exchange("/parking-spot", HttpMethod.GET,
				null, new ParameterizedTypeReference<PageableResponse<ParkingSpot>>() {
				}).getBody();
		Assertions.assertThat(parkingSpotPage).isNotNull();
		Assertions.assertThat(parkingSpotPage.toList()).isNotEmpty().hasSize(1);
		Assertions.assertThat(parkingSpotPage.toList().get(0).getResponsibleName()).isEqualTo(expectedName);
	}
}

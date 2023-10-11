package com.api.parkingcontrol.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.parkingcontrol.domain.ParkingSpot;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, UUID> {

	List<ParkingSpot> findByResponsibleName(String responsibleName);
}

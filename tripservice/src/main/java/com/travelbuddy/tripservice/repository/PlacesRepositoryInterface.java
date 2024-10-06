package com.travelbuddy.tripservice.repository;

import com.travelbuddy.tripservice.model.PlacesEntity;
import com.travelbuddy.tripservice.model.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacesRepositoryInterface extends JpaRepository<PlacesEntity, Integer> {
}

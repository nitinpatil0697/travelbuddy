package com.travelbuddy.tripservice.repository;

import com.travelbuddy.tripservice.model.PlacesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacesRepositoryInterface extends JpaRepository<PlacesEntity, Integer> {
    PlacesEntity getByPlaceId(String destination);

    PlacesEntity findByPlaceId(String code);
}

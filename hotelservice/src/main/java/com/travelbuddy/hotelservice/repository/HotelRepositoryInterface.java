package com.travelbuddy.hotelservice.repository;

import com.travelbuddy.hotelservice.model.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepositoryInterface extends JpaRepository<HotelEntity, Long> {
    HotelEntity findByHotelCode(String hotelCode);
}

package com.travelbuddy.hotelservice.repository;

import com.travelbuddy.hotelservice.model.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepositoryInterface extends JpaRepository<HotelEntity, Long> {
    HotelEntity findByHotelCode(String hotelCode);

    List<HotelEntity> findByRating(Double rating);

    List<HotelEntity> findByCity(String filterValue);

//    @Query("SELECT h FROM HotelEntity h WHERE h.cost > :cost")
//    List<HotelEntity> findHotelsByCostGreaterThan(@Param("costValue") Double cost);
//
//    @Query("SELECT h FROM HotelEntity h WHERE h.cost < :cost")
//    List<HotelEntity> findHotelsByCostLessThan(@Param("costValue") Double cost);
}

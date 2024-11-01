package com.travelbuddy.mapservice.repository;

import com.travelbuddy.mapservice.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepositoryInterface extends JpaRepository<Location, Long> {

}

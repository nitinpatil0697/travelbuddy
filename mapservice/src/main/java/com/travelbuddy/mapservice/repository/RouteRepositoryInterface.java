package com.travelbuddy.mapservice.repository;

import com.travelbuddy.mapservice.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepositoryInterface extends JpaRepository<Route, Long> {
}

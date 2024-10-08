package com.travelbuddy.tripservice.repository;

import com.travelbuddy.tripservice.model.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepositoryInterface extends JpaRepository<ExpenseEntity, Long> {
}

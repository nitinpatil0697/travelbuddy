package com.travelbuddy.tripservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "expense")
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    @Column(name = "trip_id", nullable = false)
    private Long tripId;

    @Column(name = "transport_cost")
    private Double transportCost;

    @Column(name = "hotel_cost")
    private Double hotelCost;

    @Column(name = "city_expense")
    private Double cityExpense;

    @Column(name = "other_charges")
    private Double otherCharges;

    @Column(name = "total_estimated_cost")
    private Double totalEstimatedCost;
}

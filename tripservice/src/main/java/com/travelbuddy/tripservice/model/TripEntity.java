package com.travelbuddy.tripservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "trip")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trip_name", nullable = false)
    private String tripName;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "origin", nullable = false)
    private String origin;

    @Column(name = "destination", nullable = false)
    private String destination;

    @ElementCollection
    @CollectionTable(name = "tourist_places", joinColumns = @JoinColumn(name = "trip_id"))
    @Column(name = "place")
    private List<String> touristPlaces;

    @Column(name = "trip_type")
    private String tripType;

    @Column(name = "estimated_expenses", precision = 10, scale = 2)
    private BigDecimal estimatedExpenses;
}

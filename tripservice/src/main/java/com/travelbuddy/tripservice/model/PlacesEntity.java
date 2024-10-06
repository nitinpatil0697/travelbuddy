package com.travelbuddy.tripservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "places")
public class PlacesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "entry_fee")
    private Double entryFee;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "type")
    private String type; // e.g., Historical, Adventure, Nature, etc.
}

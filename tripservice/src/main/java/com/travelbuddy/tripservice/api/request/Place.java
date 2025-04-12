package com.travelbuddy.tripservice.api.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Place {
    private String name;

    private Double estimatedCost;
}

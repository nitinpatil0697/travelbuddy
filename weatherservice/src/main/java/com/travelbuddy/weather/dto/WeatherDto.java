package com.travelbuddy.weather.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {
    private String name;
    private String description;
    private double temperature;
    private double humidity;
}
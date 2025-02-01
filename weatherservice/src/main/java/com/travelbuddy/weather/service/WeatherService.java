package com.travelbuddy.weather.service;

import com.travelbuddy.tripservice.api.response.GeneralResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface WeatherService {
    public ResponseEntity<GeneralResponse> getWeatherByCity(String city);
}

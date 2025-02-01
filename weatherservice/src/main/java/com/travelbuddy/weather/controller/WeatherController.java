package com.travelbuddy.weather.controller;

import com.travelbuddy.tripservice.api.response.GeneralResponse;
import com.travelbuddy.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("weather")
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @GetMapping("get-weather")
    public ResponseEntity<GeneralResponse> getWeather(@RequestParam("city") String city) {
        return weatherService.getWeatherByCity(city);
    }

}

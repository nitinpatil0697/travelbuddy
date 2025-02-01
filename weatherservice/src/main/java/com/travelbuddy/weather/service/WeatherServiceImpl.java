package com.travelbuddy.weather.service;

import com.travelbuddy.tripservice.api.response.GeneralResponse;
import com.travelbuddy.weather.dto.WeatherDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    public WeatherServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Fetch weather data using WebClient
    public ResponseEntity<GeneralResponse> getWeatherByCity(String city) {
        GeneralResponse generalResponse = new GeneralResponse();
        String url = String.format("%s?q=%s&appid=%s", weatherApiUrl, city, weatherApiKey);

        ResponseEntity<WeatherDto> response = restTemplate.getForEntity(url, WeatherDto.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            generalResponse.setStatus("SUCCESS");
            WeatherDto responseBody = response.getBody();
            Map <String , Object> responseData = new HashMap<>();
//            responseData.put("name" , responseBody.getName().isEmpty() ? responseBody.getName() : " ");
//            responseData.put("description" ,responseBody.getDescription());
//            responseData.put("temp" , responseBody.getTemperature());
//            responseData.put("humidity" , responseBody.getHumidity());

            generalResponse.setResult(responseData);
        } else {
            generalResponse.setStatus("FAILED");
            generalResponse.setResult(null);
        }

        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }
}


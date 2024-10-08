package com.travelbuddy.hotelservice.controller;

import com.travelbuddy.hotelservice.api.response.GeneralResponse;
import com.travelbuddy.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("allhotel")
    public ResponseEntity<GeneralResponse> getAllHotel() {
        return hotelService.getAllHotel();
    }

    @GetMapping("{code}")
    public ResponseEntity<GeneralResponse> getHotelByCode(@PathVariable("code") String code) {
        return hotelService.getHotelByCode(code);
    }
}

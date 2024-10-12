package com.travelbuddy.hotelservice.controller;

import com.travelbuddy.hotelservice.api.request.FilterParamRequest;
import com.travelbuddy.hotelservice.api.response.GeneralResponse;
import com.travelbuddy.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("filter")
    public ResponseEntity<GeneralResponse> getHotelListByFilterParam(@RequestBody FilterParamRequest filterParamReq) {
        return hotelService.getHotelByFilterParam(filterParamReq);
    }


}

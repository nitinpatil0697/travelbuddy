package com.travelbuddy.hotelservice.service;

import com.travelbuddy.hotelservice.api.request.FilterParamRequest;
import com.travelbuddy.hotelservice.api.response.GeneralResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface HotelService {
    ResponseEntity<GeneralResponse> getAllHotel();

    ResponseEntity<GeneralResponse> getHotelByCode(String code);

    ResponseEntity<GeneralResponse> getHotelByFilterParam(FilterParamRequest filterParamReq);

    ResponseEntity<GeneralResponse> getHotelByCodes(String codes);
}

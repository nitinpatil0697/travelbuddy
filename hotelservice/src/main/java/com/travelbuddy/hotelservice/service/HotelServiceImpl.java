package com.travelbuddy.hotelservice.service;

import com.travelbuddy.hotelservice.api.request.FilterParamRequest;
import com.travelbuddy.hotelservice.api.response.GeneralResponse;
import com.travelbuddy.hotelservice.model.HotelEntity;
import com.travelbuddy.hotelservice.repository.HotelRepositoryInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {
    private final HotelRepositoryInterface hotelRepositoryInterface;

    /**
     * Get All the Hotels
     *
     * @return
     */
    @Override
    public ResponseEntity<GeneralResponse> getAllHotel() {
        log.info("Get All Hotel Api called.");
        GeneralResponse hotelResponse = new GeneralResponse();
        List<HotelEntity> allHotel = hotelRepositoryInterface.findAll();
        hotelResponse.setStatus("SUCCESS");
        hotelResponse.setMessage("Hotel List fetched successfully");
        hotelResponse.setResult(allHotel);
        log.info("Hotel List fetched successfully.");
        return new ResponseEntity<>(hotelResponse, HttpStatus.OK);
    }

    /**
     * Get Hotel based on code
     *
     * @return
     */
    @Override
    public ResponseEntity<GeneralResponse> getHotelByCode(String code) {
        log.info("Get Hotel Api called.");
        GeneralResponse hotelResponse = new GeneralResponse();
        HotelEntity hotel = hotelRepositoryInterface.findByHotelCode(code);
        hotelResponse.setStatus("SUCCESS");
        hotelResponse.setMessage("Hotel fetched successfully");
        hotelResponse.setResult(hotel);
        log.info("Hotel fetched successfully.");
        return new ResponseEntity<>(hotelResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GeneralResponse> getHotelByFilterParam(FilterParamRequest filterParamReq) {
        log.info("Get Hotel Api called by filter param.");
        GeneralResponse hotelListResponse = new GeneralResponse();
        hotelListResponse.setStatus("FAILURE");
        if (!filterParamReq.getFilterField().isEmpty()) {
            String filterField = "findBy" + filterParamReq.getFilterField().substring(0, 1).toUpperCase() + filterParamReq.getFilterField().substring(1);

            try {
                // Use reflection to get the method from the repository interface
                Method method = hotelRepositoryInterface.getClass().getMethod(filterField);

                // Invoke the method dynamically
                @SuppressWarnings("unchecked")
                List<HotelEntity> allFilterHotel = (List<HotelEntity>) method.invoke(hotelRepositoryInterface);
                hotelListResponse.setStatus("SUCCESS");
                hotelListResponse.setResult(allFilterHotel);
                if (!filterParamReq.getFilterValue().isEmpty()) {
                    String methodName = "get" + filterParamReq.getFilterValue().substring(0, 1).toUpperCase() + filterParamReq.getFilterValue().substring(1);
                    List<HotelEntity> allFilteredValueHotels = allFilterHotel.stream()
                            .filter(hotel -> {
                                try {
                                    // Get the method using reflection
                                    Method methodValue = HotelEntity.class.getMethod(methodName);
                                    // Invoke the method and get the result
                                    Object value = methodValue.invoke(hotel);
                                    // Check if the value is not null and matches the filter field
                                    return value != null && value.toString().equalsIgnoreCase(filterParamReq.getFilterField());
                                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                    log.error("Method not found");
                                    return false;
                                }
                            }).toList();
                    hotelListResponse.setResult(allFilteredValueHotels);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace(); // Handle the exception properly
            }

        }
        hotelListResponse.setMessage("Hotel List fetched successfully");
        return new ResponseEntity<>(hotelListResponse, HttpStatus.OK);
    }
}

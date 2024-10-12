package com.travelbuddy.hotelservice.api.request;

import lombok.Data;

@Data
public class FilterParamRequest {
    private String filterField;
    private String filterValue;
}

package com.travelbuddy.mapservice.api.request;

import lombok.Data;

@Data
public class FilterParamRequest {
    private String filterField;
    private String filterValue;
    private String filterCondition;
}

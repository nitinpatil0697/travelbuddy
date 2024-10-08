package com.travelbuddy.hotelservice.api.response;

import lombok.Data;

@Data
public class AbstractResponse {

    private String status;
    private String message;
    private Object result;
}


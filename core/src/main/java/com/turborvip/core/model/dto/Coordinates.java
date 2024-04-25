package com.turborvip.core.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coordinates {
    private String id = null;
    private double latitude;
    private double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}


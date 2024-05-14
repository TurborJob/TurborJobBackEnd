package com.turborvip.core.service;

import com.turborvip.core.model.dto.Coordinates;
import com.uber.h3core.exceptions.DistanceUndefinedException;

import java.io.IOException;
import java.util.ArrayList;

public interface H3Service {
    double calculateDistance(Coordinates origin, Coordinates destination) throws IOException, DistanceUndefinedException;

    float sortPlaces(ArrayList<Coordinates> places, Coordinates destination);

    double haversineDistance(double lat1, double lon1, double lat2, double lon2);
}

package com.turborvip.core.service.impl;
import com.turborvip.core.model.dto.Coordinates;
import com.turborvip.core.service.H3Service;
import com.uber.h3core.H3Core;
import com.uber.h3core.exceptions.DistanceUndefinedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class H3ServiceImpl implements H3Service {
    @Override
    public double calculateDistance(Coordinates origin, Coordinates destination) throws IOException, DistanceUndefinedException {
        H3Core h3 = H3Core.newInstance();
        long originHex = h3.geoToH3(origin.getLatitude(), origin.getLongitude(), 9);
        long destinationHex = h3.geoToH3(destination.getLatitude(), destination.getLongitude(), 9);

        return h3.h3Distance(originHex, destinationHex);
    }
    @Override
    public float sortPlaces(ArrayList<Coordinates> places, Coordinates destination) {
       return 0;
    }

    @Override
    public double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in kilometers

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }
}

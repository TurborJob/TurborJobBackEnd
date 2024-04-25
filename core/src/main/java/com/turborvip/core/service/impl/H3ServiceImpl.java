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
}

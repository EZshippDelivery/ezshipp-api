package com.ezshipp.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.MatrixDistance;
import com.google.maps.model.LatLng;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class DistanceService {
   
    private GoogleMapService googleMapService;

    public Double calculateDistance(LatLng from, LatLng to) throws ServiceException {
        LatLng fromLocation = new LatLng(from.lat, from.lng);
        LatLng toLocation = new LatLng(to.lat, to.lng);
        List<MatrixDistance> matrixDistances = googleMapService.calculateDistance(new LatLng[]{fromLocation}, new LatLng[]{toLocation});
        if (!CollectionUtils.isEmpty(matrixDistances))  {
            return matrixDistances.get(0).getDistance();
        }

        return new Double("0.00");

    }
}

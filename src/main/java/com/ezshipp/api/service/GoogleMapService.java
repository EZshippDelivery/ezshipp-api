package com.ezshipp.api.service;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ezshipp.api.constants.ApplicationPropertyConfig;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.exception.ServiceExceptionCode;
import com.ezshipp.api.model.MatrixDistance;
import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElementStatus;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;

@Service
public class GoogleMapService {
	
    private final ApplicationPropertyConfig applicationPropertyConfig;

    public GoogleMapService(ApplicationPropertyConfig applicationPropertyConfig) {
        this.applicationPropertyConfig = applicationPropertyConfig;
    }
	
	 private static final String STATUS_OK = "OK";
	    private static final String EN_LANGUAGE = "en-EN";
	    private static final int DECIMAL_POINTS = 2;
	    private static final int THOUSAND = 1000;
	    private static final int SIXTY = 60;

	    
    public List<MatrixDistance> calculateDistance(LatLng[] origins, LatLng[] destinations) throws ServiceException {
        GeoApiContext context = new GeoApiContext().setApiKey(applicationPropertyConfig.getGoogleApiKey()).setQueryRateLimit(10);
        List<MatrixDistance> matrixDistanceList = new ArrayList<>();

        try {
            DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
            DistanceMatrix matrix = req.origins(origins)
                    .destinations(destinations)
                    .mode(TravelMode.DRIVING)
                    .avoid(DirectionsApi.RouteRestriction.TOLLS)
                    .units(Unit.METRIC)
                    .language(EN_LANGUAGE)
                    .await();

            for (DistanceMatrixRow row : matrix.rows) {
                if (row.elements.length > 0 && DistanceMatrixElementStatus.OK.name().equalsIgnoreCase("OK")) {
                    if (row.elements[0] != null && row.elements[0].distance != null) {
                        BigDecimal distApart = new BigDecimal(row.elements[0].distance.inMeters);
                        BigDecimal kms = distApart.divide(new BigDecimal(THOUSAND), DECIMAL_POINTS, RoundingMode.HALF_EVEN);
                        BigDecimal durationInMins = new BigDecimal(row.elements[0].duration.inSeconds)
                                .divide(new BigDecimal(SIXTY), DECIMAL_POINTS, RoundingMode.HALF_EVEN);
                        matrixDistanceList.add(new MatrixDistance(kms.doubleValue(), durationInMins.doubleValue()));
                    }
                }
            }

        } catch(ApiException e){
          //  log.error("ApiException occurred during ", e);
            throw new ServiceException(ServiceExceptionCode.INVALID_LONGITUDE_LATITUDE);
        } catch(Exception e){
          //  log.error("Exception occurred during ", e);
            throw new ServiceException(ServiceExceptionCode.INVALID_LONGITUDE_LATITUDE);
        }

        return matrixDistanceList;
    }
    
//    public List<AutoCompletePlaceResponse> autoCompletePlaces(String input) throws ServiceException {
//        GeoApiContext context = new GeoApiContext().setApiKey(applicationPropertyConfig.getGooglePlacesApiKey()).setQueryRateLimit(100);
//        try {
//            String inputWithPrefix = "hyd, " + input;
//            //log.info(applicationPropertyConfig.getGooglePlacesApiKey());
//            GooglePlaces client = new GooglePlaces(applicationPropertyConfig.getGooglePlacesApiKey());
//            //https://maps.googleapis.com/maps/api/geocode/json?address=high+st+hasting&components=country:GB&key=YOUR_API_KEY
//            Param countryParam = new Param("components");
//            //17.387140, 78.491684. - Hyderbad long lat
//            countryParam.value("country:IN");
//            Param strict = new Param("strictbounds");
//            strict.value(true);
//
//            //List<Prediction> predictions = client.getPlacePredictions(input, -1, 78, 17, -1, countryParam, strict);
//            List<Prediction> predictions = client.getPlacePredictions(input, -1, -1, -1, 100000, countryParam);
//            List<AutoCompletePlaceResponse> responseList = new ArrayList<>();
//            for (Prediction prediction : predictions) {
//                String description = prediction.getDescription();
//                Place place = prediction.getPlace(new Param("fields").value("formatted_address,geometry"));
//                AutoCompletePlaceResponse response = new AutoCompletePlaceResponse();
//                response.setLatitude(place.getLatitude());
//                response.setLongitude(place.getLongitude());
//                response.setDescription(description);
//                response.setAddress(place.getAddress());
//                response.setInput(input);
//                responseList.add(response);
////                log.debug("descriptions: " + description);
////                log.debug("address: " + place.getAddress());
////                log.debug("getLatitude: " + place.getLatitude());
////                log.debug("getLongitude: " + place.getLongitude());
////                log.debug("getName: " + place.getName());
////                log.debug("getPlaceId: " + place.getPlaceId());
////                log.debug("getVicinity: " + place.getVicinity());
////                log.debug("getTypes: " + place.getTypes());
////                log.debug("getJson: " + place.getJson());
//            }
//            return responseList;
//        } catch (Exception e) {
//            throw new ServiceException(ServiceExceptionCode.NO_PLACES_FOUND, e);
//        }
//    }
    
}

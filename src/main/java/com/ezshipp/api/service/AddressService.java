package com.ezshipp.api.service;

import static com.ezshipp.api.constants.ApplicationGlobalConstants.BIRLA_SCHOOL_ADDRESS_ID;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ezshipp.api.addressparser.HyderabadAddressParser;
import com.ezshipp.api.model.Address;
import com.ezshipp.api.model.AutoCompletePlaceResponse;
import com.ezshipp.api.model.CreateExternalOrderRequest;
import com.ezshipp.api.persistence.entity.AddressEntity;
import com.ezshipp.api.repository.AddressRepository;

@Service
public class AddressService {

	@Autowired
	AddressRepository addressRepository;
	@Autowired
	GoogleMapService googleMapService;
    public AddressEntity parseAndSave(CreateExternalOrderRequest createOrderRequest, boolean isPickup) throws Exception {
        AddressEntity addressEntity =  getAddressEntity(isPickup, createOrderRequest);

        String completeAddress = "";
        String address1 = addressEntity.getAddress1();
        String address2 = addressEntity.getAddress2();
        String pincode = createOrderRequest.getDropPinCode();

        addressEntity.setAddress1(address1);
        addressEntity.setAddress2(address2);

        if (!StringUtils.isEmpty(address1)) {
            completeAddress = completeAddress.concat(address1);
        }
        if (!StringUtils.isEmpty(address2)) {
            if (!completeAddress.isEmpty()) {
                completeAddress = completeAddress.concat(",");
            }
            completeAddress = completeAddress.concat(address2);
        }
//        if (!StringUtils.isEmpty(address3)) {
//            if (!completeAddress.isEmpty()) {
//                completeAddress = completeAddress.concat(",");
//            }
//            completeAddress = completeAddress.concat(address3);
//        }

        if (!completeAddress.isEmpty()) {
            completeAddress = completeAddress.trim();
            Address address = null;
            if (!StringUtils.isEmpty(completeAddress)) {
                address = new Address(completeAddress);
                HyderabadAddressParser parser = new HyderabadAddressParser(address);
                address = parser.parseAddress();
                for (String part : address.getSearchList()) {
                    part = part.trim();
                    List<AutoCompletePlaceResponse> responses = null;
//                    try {
//                        if (NumberUtils.isDigits(part) || part.length() <= 3) {
//                            continue;
//                        }
//                        responses = googleMapService.autoCompletePlaces(part);
//                    } catch (Exception e) {
//                        //ignore
//                    }
                    if (!CollectionUtils.isEmpty(responses)) {
                        for (AutoCompletePlaceResponse response : responses) {
                            addressEntity.setLatitude(response.getLatitude());
                            addressEntity.setLongitude(response.getLongitude());
                            //response.get
                        }
                    }
                }
            }
            if (addressEntity.getLatitude() <= 0.00) {
                addressEntity.setLatitude(0.00);
            }
            if (addressEntity.getLongitude() <= 0.00) {
                addressEntity.setLongitude(0.00);
            }
//            if (addressEntity != null && address != null && !StringUtils.isEmpty(address.getDropPinCode()) && addressEntity.getPincode() <= 0) {
//                addressEntity.setPincode(Long.valueOf(pincode));
//            }
            addressEntity.setApartment(address.getApartmentNumber());

            return addressRepository.save(addressEntity);
        }

        return null;
    }
    
    private AddressEntity getAddressEntity(boolean isPickup, CreateExternalOrderRequest createOrderRequest)  {
        AddressEntity addressEntity = new AddressEntity();

        String address1;
        String address2;
        String address3;
        String pincode;
        if (!isPickup) {
            address1 = createOrderRequest.getDropAddress1();
            address2 = createOrderRequest.getDropAddress2();
            address3 = createOrderRequest.getDropAddress3();
            pincode = createOrderRequest.getDropPinCode();
            addressEntity.setLandmark(createOrderRequest.getDropLandmark());
        } else  {
            address1 = createOrderRequest.getPickAddress1();
            address2 = createOrderRequest.getPickAddress2();
            address3 = createOrderRequest.getPickAddress3();
            pincode = createOrderRequest.getPickPinCode();
            addressEntity.setLandmark(createOrderRequest.getPickLandmark());
        }
        addressEntity.setAddress1(address1);
        addressEntity.setAddress2(address2);
        if (!StringUtils.isEmpty(address3)) {
            addressEntity.setAddress2(address2 + "," + address3);
        }
        if (!StringUtils.isEmpty(pincode)) {
            addressEntity.setPincode(Long.valueOf(pincode));
        }
        addressEntity.setCity("Hyderabad");
        addressEntity.setState("Telangana");

        return addressEntity;
    }
    
    public AddressEntity getBirlaSchoolAddress()    {
        return addressRepository.findById(BIRLA_SCHOOL_ADDRESS_ID).get();
    }
}

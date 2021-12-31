package com.ezshipp.api.service;

import static com.ezshipp.api.enums.PaymentTypeEnum.ONLINE;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezshipp.api.constants.ApplicationGlobalConstants;
import com.ezshipp.api.enums.BookingTypeEnum;
import com.ezshipp.api.enums.OrderSourceType;
import com.ezshipp.api.enums.OrderStatus;
import com.ezshipp.api.enums.OrderTypeEnum;
import com.ezshipp.api.enums.PaymentTypeEnum;
import com.ezshipp.api.enums.ZoneEnum;
import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.BusinessExceptionCode;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.CreateCustomerOrderRequest;
import com.ezshipp.api.model.CreateExternalOrderRequest;
import com.ezshipp.api.model.CreateOrderRequest;
import com.ezshipp.api.model.CustomerOrderResponse;
import com.ezshipp.api.model.OrderResponse;
import com.ezshipp.api.persistence.entity.AddressEntity;
import com.ezshipp.api.persistence.entity.CustomerEntity;
import com.ezshipp.api.persistence.entity.DriverEntity;
import com.ezshipp.api.persistence.entity.OrderCommentEntity;
import com.ezshipp.api.persistence.entity.OrderEntity;
import com.ezshipp.api.persistence.entity.PaymentEntity;
import com.ezshipp.api.persistence.entity.PremiumCustomerEntity;
import com.ezshipp.api.persistence.entity.ZoneEntity;
import com.ezshipp.api.repository.AddressRepository;
import com.ezshipp.api.repository.CustomerEntityRepository;
import com.ezshipp.api.repository.DriverEntityRepository;
import com.ezshipp.api.repository.OrderCommentEntityRepository;
import com.ezshipp.api.repository.OrderEntityRepository;
import com.ezshipp.api.repository.PaymentEntityRepository;
import com.ezshipp.api.repository.PremiumCustomerEntityRepository;
import com.ezshipp.api.repository.ZoneEntityRepository;
import com.ezshipp.api.util.OrderResponseHelper;

@Service
public class CreateOrderService {
	 private static final String ORDER_SEQID_PREFIX = "E0000";
	 
	 @Autowired
	 SequenceEntityService sequenceEntityService;
	 
	 @Autowired
	 CustomerEntityRepository customerEntityRepository;
	 
	 @Autowired
	 ZoneEntityRepository zoneEntityRepository;
	 
	 @Autowired
	 ReferenceDataMigrationService referenceDataService;
	 
	 @Autowired
	 AddressRepository addressRepository; 
	 
	 @Autowired	
	 PaymentEntityRepository paymentEntityRepository;
	 
	 @Autowired
	 OrderEntityRepository orderEntityRepository;
	 
	 @Autowired
	 PostOrderCreateService postOrderCreateService;
	 
	 @Autowired 
	 PremiumCustomerEntityRepository premiumCustomerEntityRepository;
	 
	 @Autowired
	 AddressService addressService;
	 
	 @Autowired 
	 DriverEntityRepository driverEntityRepository;
	 
	 @Autowired
	 OrderCommentEntityRepository orderCommentEntityRepository;
	 
	 @Autowired
	 OfferCodeService offerCodeService;
	
	  public OrderResponse create(CreateOrderRequest createOrderRequest) throws Exception {
	        OrderEntity orderEntity = new OrderEntity();

	        /* extract the customer */
	        CustomerEntity customerEntity = findCustomer(createOrderRequest.getCustomerId());
	        orderEntity.setCustomerByCustomerId(customerEntity);
	        orderEntity.setCustomerId(customerEntity.getId());

	        orderEntity.setOrderSeqId(generateOrderSequence(createOrderRequest.getOrderSeqId()));
	        orderEntity.setOrderCreatedTime(new Timestamp(System.currentTimeMillis()));
	        orderEntity.setOrderType(OrderTypeEnum.valueOf(createOrderRequest.getOrderType()));
	        orderEntity.setOrderSource(OrderSourceType.valueOf(createOrderRequest.getOrderSource()));

	        orderEntity.setReceiverName(createOrderRequest.getReceiverName());
	        orderEntity.setReceiverPhone(createOrderRequest.getReceiverPhone());
	        orderEntity.setSenderName(createOrderRequest.getSenderName());
	        orderEntity.setSenderPhone(createOrderRequest.getSenderPhone());

	        //TODO get the zone dynamically
	        ZoneEntity zoneEntity = extractZone(false);
	        orderEntity.setDropZoneId(zoneEntity.getId());
	        orderEntity.setZoneByDropZoneId(zoneEntity);
	        orderEntity.setPickupZoneId(zoneEntity.getId());
	        orderEntity.setZoneByPickupZoneId(zoneEntity);

	        orderEntity.setBookingId(BookingTypeEnum.valueOf(createOrderRequest.getBookingType()).ordinal());
	        orderEntity.setBookingTypeByBookingId(referenceDataService.getBookingType(orderEntity.getBookingId()));
	        orderEntity.setStatusId(OrderStatus.NEW.getStatusId());
	        orderEntity.setOrderStatusByStatusId(referenceDataService.getOrderStatus(OrderStatus.NEW.getStatusId()));

	        /* extract the addresses */
	        AddressEntity pickAddress = insertPickAddress(createOrderRequest);
	        AddressEntity dropAddress = insertDropAddress(createOrderRequest);
	        orderEntity.setAddressByPickupAddressId(pickAddress);
	        orderEntity.setAddressByDropAddressId(dropAddress);

	        /* create payment if it is online paid */
	        orderEntity.setPaymentType(PaymentTypeEnum.valueOf(createOrderRequest.getPayType()));
	        PaymentEntity paymentEntity = insertPayment(createOrderRequest, customerEntity);
	        if (null != paymentEntity) {
	            orderEntity.setPaymentByPaymentId(paymentEntity);
	            orderEntity.setPaymentId(paymentEntity.getId());
	        }
	        orderEntity.setCodCharge(createOrderRequest.getCodAmount());
	        orderEntity.setCod(createOrderRequest.getCodAmount() > 0);
	        orderEntity.setDeliveryCharge(BookingTypeEnum.valueOf(createOrderRequest.getBookingType()).getPrice());
	        orderEntity.setCollectAtPickUp(createOrderRequest.isCollectAtPickUp());
	     //   log.info("collectAtPickup: " + createOrderRequest.isCollectAtPickUp());
	        orderEntity.setWaitingTime(0);
	        orderEntity.setWeight(0);
	        orderEntity.setExternalId(createOrderRequest.getExternalId());


	        //TODO create the offer code entry in db and reference here.
	        //orderEntity.setOff(createOrderRequest.isOfferCode());

	        orderEntity.setItem(createOrderRequest.getItemName());
	        orderEntity.setItemDescription(createOrderRequest.getItemDescription());
	        orderEntity.setItemImage(createOrderRequest.getItemImageUrl());

	        orderEntity = orderEntityRepository.save(orderEntity);
	        postOrderCreateService.process(orderEntity);

	        return OrderResponseHelper.buildResponse(orderEntity);
	    }
	  
	  
	  
	  
	  public CustomerOrderResponse createExternalCustomerOrder(CreateExternalOrderRequest createOrderRequest) throws Exception{

	        OrderEntity orderEntity = new OrderEntity();

	        /* extract the customer */
	        CustomerEntity customerEntity = findCustomer(createOrderRequest.getCustomerId());
	        orderEntity.setCustomerByCustomerId(customerEntity);
	        orderEntity.setCustomerId(customerEntity.getId());

	        orderEntity.setOrderSeqId(generateOrderSequence(null));
	        orderEntity.setOrderCreatedTime(new Timestamp(System.currentTimeMillis()));
	        orderEntity.setOrderType(OrderTypeEnum.valueOf(createOrderRequest.getOrderType()));
	        orderEntity.setOrderSource(OrderSourceType.valueOf(createOrderRequest.getOrderSource()));

	        orderEntity.setReceiverName(createOrderRequest.getReceiverName());
	        orderEntity.setReceiverPhone(createOrderRequest.getReceiverPhone());


	        //TODO get the zone dynamically
	        ZoneEntity zoneEntity = extractZone(true);
	        orderEntity.setDropZoneId(zoneEntity.getId());
	        orderEntity.setZoneByDropZoneId(zoneEntity);
	        orderEntity.setPickupZoneId(zoneEntity.getId());
	        orderEntity.setZoneByPickupZoneId(zoneEntity);

	        orderEntity.setBookingId(BookingTypeEnum.getByType(createOrderRequest.getBookingType()).ordinal());
	        orderEntity.setBookingTypeByBookingId(referenceDataService.getBookingType(orderEntity.getBookingId()));
	        if (StringUtils.isEmpty(createOrderRequest.getStatus())) {
	            orderEntity.setStatusId(OrderStatus.NEW.getStatusId());
	            orderEntity.setOrderStatusByStatusId(referenceDataService.getOrderStatus(OrderStatus.NEW.getStatusId()));
	        } else { // if the order already has status, this is for past orders
	            orderEntity.setStatusId(OrderStatus.valueOf(createOrderRequest.getStatus()).getStatusId());
	            orderEntity.setOrderStatusByStatusId(referenceDataService.getOrderStatus(orderEntity.getStatusId()));
	        }
	        /* extract the addresses */
	        PremiumCustomerEntity premiumCustomerEntity = null;
	        if (customerEntity.isPremium()) {
	            premiumCustomerEntity = premiumCustomerEntityRepository.findByCustomerId(customerEntity.getId());
	            if (premiumCustomerEntity.getAddressByPickupAddress() != null) {
	                orderEntity.setAddressByPickupAddressId(premiumCustomerEntity.getAddressByPickupAddress());
	            } else {
	                if (!StringUtils.isEmpty(createOrderRequest.getPickAddress1()) ||
	                        !StringUtils.isEmpty(createOrderRequest.getPickAddress2())) {
	                    AddressEntity pickAddress = insertAddress(createOrderRequest, true);
	                    orderEntity.setAddressByPickupAddressId(pickAddress);
	                }
	            }
	            orderEntity.setExternalId(createOrderRequest.getExternalId());
	        }
	        else {
	        	if (!StringUtils.isEmpty(createOrderRequest.getPickAddress1()) ||
                        !StringUtils.isEmpty(createOrderRequest.getPickAddress2())) {
                    AddressEntity pickAddress = insertAddress(createOrderRequest, true);
                    orderEntity.setAddressByPickupAddressId(pickAddress);   
                }
	        	 orderEntity.setExternalId(createOrderRequest.getExternalId());
	        }
	        AddressEntity dropAddress = insertAddress(createOrderRequest, false);
	        if (dropAddress == null && premiumCustomerEntity != null && premiumCustomerEntity.getCompanyName().startsWith("Reinvent"))    {
	            dropAddress = addressService.getBirlaSchoolAddress();
	        }
	        orderEntity.setAddressByDropAddressId(dropAddress);

	        /* create payment if it is online paid */
	        orderEntity.setPaymentType(PaymentTypeEnum.MONTHLY);

	        orderEntity.setCodCharge(createOrderRequest.getCodAmount());
	        orderEntity.setCod(createOrderRequest.getCodAmount() > 0.00);
	        //TODO set the delivery charges based on pricing
	        orderEntity.setDeliveryCharge(createOrderRequest.getDeliveryCharge());
	        orderEntity.setWaitingTime(0);
	        orderEntity.setWeight(createOrderRequest.getWeight());

	        orderEntity.setItem(createOrderRequest.getItemName());
	        orderEntity.setItemDescription(createOrderRequest.getItemDescription());
	        orderEntity.setCreatedBy(createOrderRequest.getCreatedBy());
	        orderEntity.setCreationDate(new Timestamp(System.currentTimeMillis()));
	        orderEntity.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
	        orderEntity.setLastModifiedBy(createOrderRequest.getLastModifiedBy());
	        if (orderEntity.getDriverByDriverId() == null) {
	            DriverEntity driverEntity = driverEntityRepository.findById(ApplicationGlobalConstants.DEFAULT_DRIVER_ID).get();
	            orderEntity.setDriverByDriverId(driverEntity);
	            orderEntity.setDriverId(ApplicationGlobalConstants.DEFAULT_DRIVER_ID);
	        }

	        orderEntity = orderEntityRepository.save(orderEntity);

	        // add comments if any
	        if (!StringUtils.isEmpty(createOrderRequest.getComments())) {
	            OrderCommentEntity orderCommentEntity = new OrderCommentEntity();
	            orderCommentEntity.setComments(createOrderRequest.getComments());
	            orderCommentEntity.setOrderByOrderId(orderEntity);
	            orderCommentEntity.setCreatedBy(createOrderRequest.getCreatedBy());
	            orderCommentEntity.setCreationDate(new Timestamp(System.currentTimeMillis()));
	            orderCommentEntity.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
	            orderCommentEntity.setLastModifiedBy(createOrderRequest.getLastModifiedBy());
	            orderCommentEntityRepository.save(orderCommentEntity);
	        }

	        return OrderResponseHelper.buildCustomerOrderResponse(orderEntity);
	    }
	  
	  
	    private CustomerEntity findCustomer(Integer customerId) throws BusinessException {
	        CustomerEntity customerEntity = customerEntityRepository.findById(customerId).orElse(null);
	        if (customerEntity == null) {
	            throw new BusinessException(BusinessExceptionCode.CUSTOMER_NOT_FOUND);
	        }
	        return customerEntity;
	    }
	    
	    private String generateOrderSequence(String orderSeqId) {
	        if (!StringUtils.isEmpty(orderSeqId)) {
	            return orderSeqId;
	        }

	        return ORDER_SEQID_PREFIX + sequenceEntityService.getLastOrderSequenceId();
	    }
	  
	    
	    private ZoneEntity extractZone(boolean isExternal) {
	        //TODO dynamically zone later, now default to HitechCity
	        if (!isExternal) {
	            return zoneEntityRepository.getOne(ZoneEnum.HITECCITY.getZoneId());
	        } else {
	            return zoneEntityRepository.getOne(ZoneEnum.DILSUKHNAGAR.getZoneId());
	        }
	    }
	    
	    private AddressEntity insertPickAddress(CreateOrderRequest createOrderRequest) throws ServiceException {
	        AddressEntity addressEntity = new AddressEntity();
	        addressEntity.setAddress1(createOrderRequest.getPickAddress());
	        addressEntity.setLatitude(createOrderRequest.getPickLatitude());
	        addressEntity.setLongitude(createOrderRequest.getPickLongitude());
	        addressEntity.setLandmark(createOrderRequest.getPickLandmark());
	        addressEntity.setCity("Hyderabad");
	        addressEntity.setState("Telangana");
	        addressEntity.setApartment(createOrderRequest.getPickFlat());
	        return addressRepository.save(addressEntity);

	    }

	    private AddressEntity insertDropAddress(CreateOrderRequest createOrderRequest) throws ServiceException {
	        AddressEntity addressEntity = new AddressEntity();
	        addressEntity.setAddress1(createOrderRequest.getDropAddress());
	        addressEntity.setLatitude(createOrderRequest.getDropLatitude());
	        addressEntity.setLongitude(createOrderRequest.getDropLongitude());
	        addressEntity.setLandmark(createOrderRequest.getDropLandmark());
	        addressEntity.setCity("Hyderabad");
	        addressEntity.setState("Telangana");
	        addressEntity.setApartment(createOrderRequest.getDropFlat());
	        return addressRepository.save(addressEntity);
	    }
	    
	    private PaymentEntity insertPayment(CreateOrderRequest request, CustomerEntity customerEntity) {
	        if (PaymentTypeEnum.valueOf(request.getPayType()) == ONLINE && !isEmpty(request.getPaymentId())) {
	            PaymentEntity paymentEntity = new PaymentEntity();
	            paymentEntity.setAmount(request.getDeliveryCharge());
	            paymentEntity.setPaymentId(request.getPaymentId());
	            paymentEntity.setCaptureTime(new Timestamp(System.currentTimeMillis()));
	            paymentEntity.setCaptured(false);
	            paymentEntity.setRefunded(false);
	            paymentEntity.setCustomerId(request.getCustomerId());
	            paymentEntity.setCustomerByCustomerId(customerEntity);
	            return paymentEntityRepository.save(paymentEntity);
	        }

	        return null;
	    }
	    
	    private AddressEntity insertAddress(CreateExternalOrderRequest createOrderRequest, boolean isPickAddress) throws Exception {
	        return addressService.parseAndSave(createOrderRequest, isPickAddress);
	    }
	    
	    public static boolean isEmpty(String str) {
	        if(str != null && !str.isEmpty())
	            return false;
	        return true;
	    }
	    
	    
	    public CustomerOrderResponse createCustomerOrder(CreateCustomerOrderRequest createOrderRequest) throws BusinessException, ServiceException {
	        OrderEntity orderEntity = new OrderEntity();
	        /* set the customer */
	        CustomerEntity customerEntity = findCustomer(createOrderRequest.getCustomerId());
	        orderEntity.setCustomerByCustomerId(customerEntity);
	        orderEntity.setCustomerId(createOrderRequest.getCustomerId());

	        orderEntity.setOrderSeqId(generateOrderSequence(null));
	        orderEntity.setOrderCreatedTime(new Timestamp(System.currentTimeMillis()));
	        orderEntity.setOrderType(OrderTypeEnum.valueOf(createOrderRequest.getOrderType()));
	        orderEntity.setOrderSource(OrderSourceType.valueOf(createOrderRequest.getOrderSource()));

	        orderEntity.setReceiverName(createOrderRequest.getReceiverName());
	        orderEntity.setReceiverPhone(createOrderRequest.getReceiverPhone());
	        orderEntity.setSenderName(createOrderRequest.getSenderName());
	        orderEntity.setSenderPhone(createOrderRequest.getSenderPhone());

	        //TODO get the zone dynamically
	        ZoneEntity zoneEntity = extractZone(false);
	        orderEntity.setDropZoneId(zoneEntity.getId());
	        orderEntity.setZoneByDropZoneId(zoneEntity);
	        orderEntity.setPickupZoneId(zoneEntity.getId());
	        orderEntity.setZoneByPickupZoneId(zoneEntity);

	        orderEntity.setBookingId(BookingTypeEnum.valueOf(createOrderRequest.getBookingType()).ordinal());
	        orderEntity.setBookingTypeByBookingId(referenceDataService.getBookingType(orderEntity.getBookingId()));
	        orderEntity.setStatusId(OrderStatus.NEW.getStatusId());
	        orderEntity.setOrderStatusByStatusId(referenceDataService.getOrderStatus(OrderStatus.NEW.getStatusId()));

	        /* extract the addresses */
	        AddressEntity pickAddress = addressRepository.getById(createOrderRequest.getPickAddressId());
	        AddressEntity dropAddress = addressRepository.getById(createOrderRequest.getDeliveryAddressId());
	        orderEntity.setAddressByPickupAddressId(pickAddress);
	        orderEntity.setAddressByDropAddressId(dropAddress);

	        /* create payment if it is online paid */
	        orderEntity.setPaymentType(PaymentTypeEnum.valueOf(createOrderRequest.getPayType()));
	        PaymentEntity paymentEntity = insertPayment(createOrderRequest, customerEntity);
	        if (null != paymentEntity) {
	            orderEntity.setPaymentByPaymentId(paymentEntity);
	            orderEntity.setPaymentId(paymentEntity.getId());
	        }
	        orderEntity.setCodCharge(createOrderRequest.getCodAmount());
	        orderEntity.setCod(createOrderRequest.getCodAmount() > 0);
	        if (!customerEntity.isPremium()) {
	            orderEntity.setDeliveryCharge(createOrderRequest.getDeliveryCharge());
	        } else {
	            orderEntity.setDeliveryCharge(0.00);
	            orderEntity.setPaymentType(PaymentTypeEnum.MONTHLY);
	        }

	        orderEntity.setCollectAtPickUp(createOrderRequest.isCollectAtPickUp());
	        orderEntity.setWaitingTime(0);
	        orderEntity.setExternalId(createOrderRequest.getExternalId());
	        //TODO create the offer code entry in db and reference here.
	        updateOfferCodeUsed(createOrderRequest.getOfferId(), createOrderRequest.getCustomerId());

	        orderEntity.setItem(createOrderRequest.getItemName());
	        orderEntity.setItemDescription(createOrderRequest.getItemDescription());
	        orderEntity.setItemImage(createOrderRequest.getItemImageUrl());

	        orderEntity = orderEntityRepository.save(orderEntity);
	        postOrderCreateService.process(orderEntity);

	        return OrderResponseHelper.buildCustomerOrderResponse(orderEntity);
	    }
	    
	    
	    private PaymentEntity insertPayment(CreateCustomerOrderRequest request, CustomerEntity customerEntity) {
	        if (PaymentTypeEnum.valueOf(request.getPayType()) == ONLINE && !isEmpty(request.getPaymentId())) {
	            PaymentEntity paymentEntity = new PaymentEntity();
	            paymentEntity.setAmount(request.getDeliveryCharge());
	            paymentEntity.setPaymentId(request.getPaymentId());
	            paymentEntity.setCaptureTime(new Timestamp(System.currentTimeMillis()));
	            paymentEntity.setCaptured(false);
	            paymentEntity.setRefunded(false);
	            paymentEntity.setCustomerId(request.getCustomerId());
	            paymentEntity.setCustomerByCustomerId(customerEntity);
	            return paymentEntityRepository.save(paymentEntity);
	        }

	        return null;
	    }
	    
	    private void updateOfferCodeUsed(int offerId, int customerId) throws ServiceException   {
	        if (offerId > 0) {
	            offerCodeService.addOfferCodeUsedByCustomer(offerId, customerId);
	        }
	    }
	   
}

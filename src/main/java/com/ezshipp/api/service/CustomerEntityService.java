package com.ezshipp.api.service;

import static com.ezshipp.api.enums.AddressType.HOME;
import static com.ezshipp.api.enums.AddressType.OFFICE;
import static com.ezshipp.api.enums.AddressType.OTHER;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ezshipp.api.enums.OrderStatus;
import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.BusinessExceptionCode;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.helper.CustomerResponseHelper;
import com.ezshipp.api.model.BaseFilter;
import com.ezshipp.api.model.CustomerAddressRequest;
import com.ezshipp.api.model.CustomerAddressResponse;
import com.ezshipp.api.model.CustomerOrderResponse;
import com.ezshipp.api.model.CustomerResponse;
import com.ezshipp.api.model.PagingResponse;
import com.ezshipp.api.model.ProfileResponse;
import com.ezshipp.api.model.UpdateCustomerRequest;
import com.ezshipp.api.persistence.entity.AddressEntity;
import com.ezshipp.api.persistence.entity.CustomerAddressEntity;
import com.ezshipp.api.persistence.entity.CustomerEntity;
import com.ezshipp.api.persistence.entity.OrderEntity;
import com.ezshipp.api.persistence.entity.PremiumCustomerEntity;
import com.ezshipp.api.repository.AddressRepository;
import com.ezshipp.api.repository.CustomerAddressEntityRepository;
import com.ezshipp.api.repository.CustomerEntityRepository;
import com.ezshipp.api.repository.OrderEntityRepository;
import com.ezshipp.api.repository.PremiumCustomerEntityRepository;
import com.ezshipp.api.util.OrderResponseHelper;

@Service
public class CustomerEntityService extends BaseEntityService{

	@Autowired
	CustomerEntityRepository customerEntityRepository;
	
	@Autowired
	PremiumCustomerEntityRepository premiumCustomerEntityRepository;
	
	@Autowired
	CustomerAddressEntityRepository customerAddressEntityRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	OrderEntityRepository orderEntityRepository;
	
	  public ProfileResponse findById(Integer customerId) throws ServiceException, BusinessException  {
	        if (customerId <= 0) {
	        	 throw new BusinessException(BusinessExceptionCode.INVALID_CUSTOMER_ID);
	        }

	        CustomerEntity customerEntity = customerEntityRepository.findCustomerEntityById(customerId);
	        if (customerEntity == null) {
	        	throw new BusinessException(BusinessExceptionCode.INVALID_CUSTOMER_ID);
	        }

	        ProfileResponse profileResponse = new ProfileResponse();
	        profileResponse.setCustomerId(customerEntity.getId());
	        profileResponse.setUsername(customerEntity.getUserByUserId().getUsername());
	        profileResponse.setFirstName(customerEntity.getUserByUserId().getFirstName());
	        profileResponse.setLastName(customerEntity.getUserByUserId().getLastName());
	        profileResponse.setReferralCode(customerEntity.getReferralCode());
	        profileResponse.setEmail(customerEntity.getUserByUserId().getEmail());
	        profileResponse.setPhone(customerEntity.getUserByUserId().getPhone());
	        profileResponse.setUserType(customerEntity.getUserByUserId().getUserType());
	        profileResponse.setName(customerEntity.getUserByUserId().getFirstName() + " " + customerEntity.getUserByUserId().getLastName());
	        if (customerEntity.isPremium()) {
	            PremiumCustomerEntity premiumCustomerEntity = premiumCustomerEntityRepository.findByCustomerId(customerEntity.getId());
	            if (premiumCustomerEntity != null) {
	                profileResponse.setPremium(true);
	                profileResponse.setReceivePush(premiumCustomerEntity.isNotifySms());
	                profileResponse.setReceiveSMS(premiumCustomerEntity.isNotifySms());
	                profileResponse.setReceiveEmail(premiumCustomerEntity.isNotifyEmail());
	            }
	        }
	        return profileResponse;
	    }
	  
	  
	  public List<CustomerAddressResponse> getFirstTopTenAddresses(Integer customerId) throws Exception {
	        List<CustomerAddressResponse> customerAddressResponseList = new ArrayList<>();
	        if (customerEntityRepository.findById(customerId).isPresent()) {
	            List<CustomerAddressEntity> customerAddressEntities = customerAddressEntityRepository.findTop10ByCustomerIdOrderByAddressIdDesc(customerId);
	            for (CustomerAddressEntity customerAddressEntity : customerAddressEntities) {
	                CustomerAddressResponse response = new CustomerAddressResponse();
	                response.setAddressId(customerAddressEntity.getAddressId());
	                response.setCustomerId(customerAddressEntity.getCustomerId());
	                response.setAddress1(customerAddressEntity.getAddressByAddressId().getAddress1());
	                response.setAddress2(customerAddressEntity.getAddressByAddressId().getAddress2());
	                response.setAddressType(customerAddressEntity.getAddressType());
	                response.setApartment(customerAddressEntity.getAddressByAddressId().getApartment());
	                response.setLandmark(customerAddressEntity.getAddressByAddressId().getLandmark());
	                response.setLatitude(customerAddressEntity.getAddressByAddressId().getLatitude());
	                response.setLongitude(customerAddressEntity.getAddressByAddressId().getLongitude());
	                response.setCity(customerAddressEntity.getAddressByAddressId().getCity());
	                response.setPincode(customerAddressEntity.getAddressByAddressId().getPincode());
	                response.setState(customerAddressEntity.getAddressByAddressId().getState());
	                customerAddressResponseList.add(response);
	            }
	        } else {
	            throw new Exception();
	        }
	        return customerAddressResponseList;
	    }
	  
	  
	  public Integer createCustomerAddress(CustomerAddressRequest request) {
	        List<AddressEntity> existingAddressesWithSameAddress1 = addressRepository.findAddressEntitiesByAddress1(request.getAddress1().trim());
	        AddressEntity addressEntity = null;
	        if (existingAddressesWithSameAddress1.isEmpty()) {
	            addressEntity = new AddressEntity();
	            addressEntity.setAddress1(request.getAddress1());
	            addressEntity.setAddress2(request.getAddress2());
	            addressEntity.setLandmark(request.getLandmark());
	            addressEntity.setLatitude(request.getLatitude());
	            addressEntity.setApartment(request.getApartment());
	            addressEntity.setCity(request.getCity());
	            addressEntity.setComplexName(request.getComplexName());
	            addressEntity.setPincode(request.getPincode());
	            addressEntity.setState(request.getState());
	            addressEntity.setLongitude(request.getLongitude());
	            addressEntity.setLastModifiedBy(request.getCustomerId());
	            addressEntity.setCreatedBy(request.getCustomerId());

	            addressEntity = addressRepository.save(addressEntity);
	            System.out.println(addressEntity.getAddress1());
	        } else {
	            addressEntity = existingAddressesWithSameAddress1.get(0);
	            System.out.println(addressEntity.getAddress1());
	        }

	        // check if any existing address is already tagged as Home or Office
	        if (request.getType() == HOME || request.getType() == OFFICE) {
	            List<CustomerAddressEntity> entities = customerAddressEntityRepository.findByCustomerIdAndAddressType(request.getCustomerId(), request.getType());
	            if (entities.size() > 0) {
	                for (CustomerAddressEntity entity : entities) {
	                    entity.setAddressType(OTHER);
	                }
	                customerAddressEntityRepository.saveAll(entities);
	            }
	        }

	        CustomerAddressEntity customerAddressEntity = new CustomerAddressEntity();
	        customerAddressEntity.setCustomerId(request.getCustomerId());
	        customerAddressEntity.setAddressByAddressId(addressEntity);
	        customerAddressEntity.setAddressType(request.getType());
	        customerAddressEntity.setCustomerByCustomerId(customerEntityRepository.getOne(request.getCustomerId()));
	        customerAddressEntityRepository.save(customerAddressEntity);
	        return addressEntity.getId();
	    }
	  
	  
	  public CustomerResponse updateCustomer(UpdateCustomerRequest updateCustomerRequest) throws Exception  {
	        validateID(updateCustomerRequest.getCustomerId());

	        CustomerEntity c = customerEntityRepository.getOne(updateCustomerRequest.getCustomerId());

	        if (c == null) {
	            throw new Exception();
	        }

	        if (!StringUtils.isEmpty(updateCustomerRequest.getFirstName())) {
	            c.getUserByUserId().setFirstName(updateCustomerRequest.getFirstName());
	        }

	        if (!StringUtils.isEmpty(updateCustomerRequest.getLastName())) {
	            c.getUserByUserId().setLastName(updateCustomerRequest.getLastName());
	        }

	        if (!StringUtils.isEmpty(updateCustomerRequest.getEmail())) {
	            c.getUserByUserId().setEmail(updateCustomerRequest.getEmail());
	        }

	        if (!StringUtils.isEmpty(updateCustomerRequest.getDeviceToken())) {
	            if (c.getDeviceByDeviceId() != null) {
	                c.getDeviceByDeviceId().setDeviceToken(updateCustomerRequest.getDeviceToken());
	            }
	        }

	        if (!StringUtils.isEmpty(updateCustomerRequest.getDeviceToken())) {
	            if (c.getDeviceByDeviceId() != null) {
	                c.getDeviceByDeviceId().setDeviceToken(updateCustomerRequest.getDeviceToken());
	            }
	        }

	        if (c.isPremium()) {
	            PremiumCustomerEntity premiumCustomerEntity = premiumCustomerEntityRepository.findByCustomerId(c.getId());
	            if (premiumCustomerEntity != null) {
	                if (updateCustomerRequest.isReceivePush() != premiumCustomerEntity.isNotifyPush()) {
	                    premiumCustomerEntity.setNotifyPush(updateCustomerRequest.isReceivePush());
	                }
	                if (updateCustomerRequest.isReceiveSMS() != premiumCustomerEntity.isNotifySms()) {
	                    premiumCustomerEntity.setNotifySms(updateCustomerRequest.isReceiveSMS());
	                }
	                if (updateCustomerRequest.isReceiveEmail() != premiumCustomerEntity.isNotifyEmail()) {
	                    premiumCustomerEntity.setNotifyEmail(updateCustomerRequest.isReceiveEmail());
	                }
	                premiumCustomerEntityRepository.save(premiumCustomerEntity);
	            }
	        }

	        customerEntityRepository.save(c);

	        return CustomerResponseHelper.buildResponse(c);
	    }
	  
	  public List<CustomerOrderResponse> getCustomerOrderHistory(Integer customerId) throws Exception {
	        validateID(customerId);

	        List<OrderEntity> orderEntities = orderEntityRepository.findTop10ByCustomerIdOrderByOrderCreatedTimeDesc(customerId);
	        List<CustomerOrderResponse> orderHistoryResponseList = new ArrayList<>();
	        for (OrderEntity entity : orderEntities) {
	            orderHistoryResponseList.add(OrderResponseHelper.buildCustomerOrderResponse(entity));
	        }
	        return orderHistoryResponseList;
	    }
	  
	  public void deleteCustomerAddress(Integer customerId, Integer addressId) throws Exception {
	        validateID(customerId);

	        customerAddressEntityRepository.deleteAddressByCustomerIdAndAddressId(customerId, addressId);
	    }
	  
	  
	   public PagingResponse getCustomerOrders(Integer customerId, int pageSize, int pageNumber) throws BusinessException, ServiceException {
	        validateID(customerId);

	        Page<OrderEntity> orderEntities = orderEntityRepository.findAllByCustomerId(customerId, createPageRequest(pageNumber, pageSize));
	        List<OrderEntity> orderEntityList = orderEntities.getContent();
	        List<CustomerOrderResponse> orderResponseList = new ArrayList<>();
	        for (OrderEntity entity : orderEntityList) {
	            orderResponseList.add(OrderResponseHelper.buildCustomerOrderResponse(entity));
	        }

	        PagingResponse pagingResponse = buildPagingResponse(orderEntities, new BaseFilter(pageNumber, pageSize));
	        pagingResponse.setMessage("success customer response");
	        pagingResponse.setData(orderResponseList);
	        return pagingResponse;
	    }
	  
	   public Integer getCustomerInProgressOrdersCount(Integer customerId) throws BusinessException, ServiceException {
	        validateID(customerId);

	        List<Integer> statuses = new ArrayList<>();
	        statuses.add(OrderStatus.CANCELLED.getStatusId());
	        statuses.add(OrderStatus.DELIVERED.getStatusId());
	        statuses.add(OrderStatus.EXPIRED.getStatusId());
	        statuses.add(OrderStatus.REJECTED.getStatusId());

	        return orderEntityRepository.countByCustomerIdAndStatusIdNotIn(customerId, statuses);
	    }


	public List<CustomerEntity> getAllNonPremiumCustomers() {
        return customerEntityRepository.findAllByPremiumFalse();
    }
	 
}

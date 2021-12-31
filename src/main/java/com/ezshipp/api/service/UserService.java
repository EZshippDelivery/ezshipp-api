package com.ezshipp.api.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ezshipp.api.enums.UserType;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.BaseFilter;
import com.ezshipp.api.model.UserResponse;
import com.ezshipp.api.persistence.entity.CustomerEntity;
import com.ezshipp.api.persistence.entity.UserEntity;
import com.ezshipp.api.repository.CustomerEntityRepository;
import com.ezshipp.api.repository.UserRepository;
import com.ezshipp.api.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final CustomerEntityRepository customerEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public UserService(UserRepository userRepository, CustomerEntityRepository customerEntityRepository) {
        this.userRepository = userRepository;
        this.customerEntityRepository = customerEntityRepository;
    }

    public UserEntity findById(int id) {
        Optional<UserEntity> userData = userRepository.findById(id);
        if (userData.isPresent())    {
            return userData.get();
        }
        return new UserEntity();
    }

    public UserEntity findByEmail(String email) throws ServiceException {
        Query query = entityManager.createNamedQuery("findUserByEmail", UserEntity.class);
        return (UserEntity) query.getSingleResult();
    }

    public Optional<UserEntity> findByUserName(String username)  {
        Query query = entityManager.createNamedQuery("findUserByUserName", UserEntity.class);
        Optional<UserEntity> optionalUserEntity = Optional.of((UserEntity) query.getSingleResult());
        return optionalUserEntity;
    }

    public List<UserResponse> findTodayCreatedUsers(BaseFilter baseFilter)  {
        Timestamp startDate;
        Timestamp endDate;
        if (baseFilter.getStartDate() != null && baseFilter.getEndDate() != null) {
            startDate = DateUtil.getOneAM(baseFilter.getStartDate());
            endDate = DateUtil.getMidnight(baseFilter.getEndDate());
        } else  {
            startDate = new Timestamp(DateUtil.getTodayStartDateTime(0).getTime());
            endDate = new Timestamp(DateUtil.getTodayEndDateTime(0).getTime());
        }

        List<UserEntity> userEntityList = userRepository.findByUserTypeAndCreatedTimeBetween(UserType.CUSTOMER, startDate, endDate);
        List<UserResponse> userResponseList = new ArrayList<>();
        for (UserEntity entity : userEntityList) {
            UserResponse userResponse = new UserResponse();
            userResponse.setCreatedTime(entity.getCreatedTime());
            userResponse.setName(entity.getFirstName() + " " + entity.getLastName());
            userResponse.setPhone(String.valueOf(entity.getPhone()));
            CustomerEntity customerEntity = customerEntityRepository.findCustomerEntityByUserId(entity.getId());
            if (customerEntity != null && customerEntity.getDeviceByDeviceId() != null) {
                userResponse.setSource(customerEntity.getDeviceByDeviceId().getDeviceType().name());
            } else  {
                userResponse.setSource("WEB");
            }
            userResponseList.add(userResponse);
        }
        return userResponseList;
    }

//    public List<CustomerResponse> findAll(SearchCriteria search) {
//        GenericSpecificationsBuilder<UserEntity> builder = new GenericSpecificationsBuilder<>();
//        builder.with(search.getKey(), search.getOperation(), search.getValue(), "", "");
//        //Specification<UserEntity> spec = builder.build(Function::
//        Specification<UserEntity> spec = builder.build(UserSpecification.class.getName());
//        List<UserEntity> results = userRepository.findAll(spec);
//
//        List<CustomerResponse> customerResponseList = new ArrayList<>();
//        Map<Integer, UserEntity> idsMap = results.stream().collect(Collectors.toMap(UserEntity::getId, u -> u));
//        List<CustomerEntity> entities = getCustomersByIds(new ArrayList<>(idsMap.keySet()));
//        for (CustomerEntity entity : entities) {
//            customerResponseList.add(CustomerResponseHelper.buildResponse(entity));
//        }
//
//        return customerResponseList;
//    }

    private List<CustomerEntity> getCustomersByIds(List<Integer> ids)   {
        return customerEntityRepository.findAllByIdIn(ids);
    }

}

//package com.ezshipp.api.service;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import javax.inject.Inject;
//
//import org.apache.commons.lang3.text.WordUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.ezshipp.api.document.AdminUser;
//import com.ezshipp.api.document.Employee;
//import com.ezshipp.api.document.Zone;
//import com.ezshipp.api.enums.AuthType;
//import com.ezshipp.api.enums.EmployeeType;
//import com.ezshipp.api.enums.GenderEnum;
//import com.ezshipp.api.enums.UserType;
//import com.ezshipp.api.exception.ServiceException;
//import com.ezshipp.api.persistence.entity.DriverEntity;
//import com.ezshipp.api.persistence.entity.EmployeeEntity;
//import com.ezshipp.api.persistence.entity.UserEntity;
//import com.ezshipp.api.repositories.EmployeeRepository;
//import com.ezshipp.api.repositories.ZoneRepository;
//import com.ezshipp.api.repository.DriverEntityRepository;
//import com.ezshipp.api.repository.EmployeeEntityRepository;
//import com.ezshipp.api.repository.UserRepository;
//import com.ezshipp.api.service.BikerService;
//import com.mysql.cj.jdbc.Driver;
//
//import lombok.extern.slf4j.Slf4j;
//
//
//@Service
//@Slf4j
//public class UserMigrationService {
//
//    private final UserRepository userRepository;
//
//    private final DriverEntityRepository driverEntityRepository;
//
//    private final UserService userService;
//
//    private final BikerService bikerService;
//
//    private final ZoneRepository zoneRepository;
//
//    private final EmployeeRepository employeeRepository;
//
//    private final EmployeeEntityRepository employeeEntityRepository;
//
//    @Inject
//    public UserMigrationService(UserRepository userRepository, DriverEntityRepository driverEntityRepository, UserService userService, BikerService bikerService, ZoneRepository zoneRepository, EmployeeRepository employeeRepository, EmployeeEntityRepository employeeEntityRepository) {
//        this.userRepository = userRepository;
//        this.driverEntityRepository = driverEntityRepository;
//        this.userService = userService;
//        this.bikerService = bikerService;
//        this.zoneRepository = zoneRepository;
//        this.employeeRepository = employeeRepository;
//        this.employeeEntityRepository = employeeEntityRepository;
//    }
//
//    @Transactional
//    public void migrateUsersAndBikers(List<AdminUser> adminUsers, List<Driver> drivers)  throws ServiceException {
//        UserEntity masterUser = userRepository.findByEmail("contact@ezshipp.com").get();
//        UserEntity systemUser = userRepository.findByEmail("developer@ezshipp.com").get();
//        UserEntity migrationUser = userRepository.findByEmail("migration@ezshipp.com").get();
//
//
//        List<UserEntity> userEntities = new ArrayList<>();
//        for (AdminUser adminUser : adminUsers) {
//            UserEntity userEntity = new UserEntity();
//            userEntity.setActive(adminUser.isStatus());
//            userEntity.setEmail(adminUser.getEmail());
//            userEntity.setFirstName(WordUtils.capitalize(adminUser.getName().split(" ")[0]).trim());
//            userEntity.setLastName(WordUtils.capitalize(adminUser.getName().split(" ")[1]).trim());
//            userEntities.add(userEntity);
//        }
//
//        for (Driver driver : drivers) {
//            UserEntity userEntity = new UserEntity();
//            userEntity.setActive(driver.getCurrentStatus() == 1);
//            userEntity.setEmail(driver.getEmail());
//            userEntity.setFirstName(WordUtils.capitalize(driver.getName()));
//            userEntity.setLastName(WordUtils.capitalize(driver.getLname()));
//            userEntity.setCreatedBy(44);
//            userEntities.add(userEntity);
//        }
//        userEntities = userRepository.saveAll(userEntities);
//        Map<String, UserEntity> userEntityMap = userEntities.stream().collect(Collectors.toMap(UserEntity::getEmail, u -> u));
//
//        List<DriverEntity> driverEntities = new ArrayList<>();
//        for (Driver driver : drivers) {
//            DriverEntity driverEntity = new DriverEntity();
//            UserEntity userEntity = userEntityMap.get(driver.getEmail());
//            driverEntity.setUserByUserId(userEntity);
//            driverEntity.setUserId(userEntity.getId());
//            driverEntity.setActive(userEntity.isActive());
//            driverEntity.setCreatedBy(44);
//            driverEntity.setCreatedTime(new Timestamp(System.currentTimeMillis()));
//            driverEntity.setModifiedTime(new Timestamp(System.currentTimeMillis()));
//            driverEntity.setModifiedBy(44);
//            Zone zone = zoneRepository.findById(driver.getDepoId()).get();
//            //driverEntity.setZoneId(ZoneEnum.getById(zone.getZoneseq()).getZoneId());
//            driverEntities.add(driverEntity);
//        }
//        driverEntityRepository.saveAll(driverEntities);
//    }
//
//    @Transactional
//    public void createSystemUsers()  {
//        UserEntity masterUser = new UserEntity();
//        masterUser.setActive(true);
//        masterUser.setEmail("contact@ezshipp.com");
//        masterUser.setFirstName("Master");
//        masterUser.setLastName("User");
//        masterUser.setPassword("ezshipp@123");
//        masterUser.setActive(true);
//        masterUser.setAuthType(AuthType.EMAIL);
//        masterUser.setCreatedBy(0);
//        masterUser.setUserType(UserType.EMPLOYEE);
//        masterUser.setUsername("contact@ezshipp.com");
//        masterUser = userRepository.save(masterUser);
//
//        UserEntity systemUser = new UserEntity();
//        systemUser.setActive(true);
//        systemUser.setEmail("developer@ezshipp.com");
//        systemUser.setFirstName("System");
//        systemUser.setLastName("User");
//        systemUser.setPassword("ezshipp@123");
//        systemUser.setActive(true);
//        systemUser.setAuthType(AuthType.EMAIL);
//        systemUser.setUserType(UserType.EMPLOYEE);
//        systemUser.setUsername("developer@ezshipp.com");
//        systemUser.setCreatedBy(masterUser.getId());
//        systemUser = userRepository.save(systemUser);
//
//        UserEntity migrationUser = new UserEntity();
//        migrationUser.setActive(true);
//        migrationUser.setPassword("ezshipp@123");
//        migrationUser.setEmail("migration@ezshipp.com");
//        migrationUser.setFirstName("Migration");
//        migrationUser.setLastName("User");
//        migrationUser.setPassword("ezshipp@123");
//        migrationUser.setActive(true);
//        migrationUser.setAuthType(AuthType.EMAIL);
//        migrationUser.setUserType(UserType.EMPLOYEE);
//        migrationUser.setUsername("migration@ezshipp.com");
//        migrationUser.setCreatedBy(masterUser.getId());
//        migrationUser = userRepository.save(migrationUser);
//
//        log.info("Master User Id: " + masterUser.getId());
//        log.info("System User Id: " + systemUser.getId());
//        log.info("Migration User Id: " + migrationUser.getId());
//    }
//
//    @Transactional
//    public void migrateEmployees(List<Employee> employees)  throws ServiceException {
//        List<EmployeeEntity> employeeEntities = new ArrayList<>();
//        for (Employee employee : employees) {
//            EmployeeEntity employeeEntity = new EmployeeEntity();
//            UserEntity userEntity = userService.findByEmail(employee.getEmployee_Email());
//            employeeEntity.setUserId(userEntity.getId());
//            employeeEntity.setUserByUserId(userEntity);
//            employeeEntity.setPanNumber(employee.getPan_Card_Number());
//            employeeEntity.setGender(GenderEnum.values()[employee.getEmployee_Gender()]);
//            employeeEntity.setEmployeeType(EmployeeType.values()[employee.getEmployee_Role()]);
//            employeeEntity.setBankAccountNumber(Long.valueOf(employee.getBank_Account_No()));
//            employeeEntity.setBankIfscCode(employee.getBank_IFSC_No());
//            employeeEntity.setBankName(employee.getBank_Name());
//            employeeEntity.setBaseSalary(employee.getEmployee_Basic_Salary());
//            employeeEntity.setPhoneNumber(employee.getEmployee_PhoneNumber());
//            employeeEntity.setCreatedBy(44);
//            employeeEntity.setCreationDate(new Timestamp(System.currentTimeMillis()));
//            employeeEntity.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
//            employeeEntity.setLastModifiedBy(44);
//            employeeEntity.setEmployeeId(employee.getEmployee_Company_ID());
//            employeeEntities.add(employeeEntity);
//
//        }
//        employeeEntityRepository.saveAll(employeeEntities);
//    }
//
//    @Transactional
//    public void migrateDrivers(List<Driver> drivers)  throws ServiceException {
//        List<DriverEntity> driverEntities = new ArrayList<>();
//        for (Driver driver : drivers) {
//            DriverEntity driverEntity = new DriverEntity();
//            driverEntity.setActive(driver.getStatus() == 1);
//            UserEntity userEntity = userService.findByEmail(driver.getEmail());
//            driverEntity.setUserId(userEntity.getId());
//            driverEntity.setUserByUserId(userEntity);
//            driverEntity.setCreatedBy(44);
//            driverEntity.setCreatedTime(new Timestamp(System.currentTimeMillis()));
//            driverEntity.setModifiedTime(new Timestamp(System.currentTimeMillis()));
//            driverEntity.setModifiedBy(44);
//            driverEntities.add(driverEntity);
//        }
//        driverEntityRepository.saveAll(driverEntities);
//    }
//
//
//}

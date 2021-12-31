package com.ezshipp.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezshipp.api.enums.RoleEnum;
import com.ezshipp.api.persistence.entity.RoleEntity;
import com.ezshipp.api.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;

	 public Optional<Object> findByName(RoleEnum roleEnum)  {
//       Query query = entityManager.createNamedQuery("findRoleByName", RoleEntity.class);
//       Optional<RoleEntity> optionalUserEntity = Optional.of((RoleEntity) query.getSingleResult());
       return Optional.of(roleRepository.findByName(roleEnum.name()));
   }
}

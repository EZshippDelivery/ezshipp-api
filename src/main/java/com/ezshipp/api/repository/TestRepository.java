package com.ezshipp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezshipp.api.model.TestBean;

public interface TestRepository extends JpaRepository<TestBean,Long>{

}

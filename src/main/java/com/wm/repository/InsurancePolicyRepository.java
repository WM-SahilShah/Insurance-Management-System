package com.wm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wm.model.InsurancePolicy;

public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, Integer>{

}

package com.wm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wm.model.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Integer>{

}

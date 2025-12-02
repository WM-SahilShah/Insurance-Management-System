package com.wm.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wm.dto.InsurancePolicyDto;
import com.wm.exception.ResourceNotFoundException;
import com.wm.model.Client;
import com.wm.model.InsurancePolicy;
import com.wm.repository.InsurancePolicyRepository;

import jakarta.transaction.Transactional;

@Service
public class InsurancePolicyServiceImpl implements InsurancePolicyService {

	@Autowired
	private InsurancePolicyRepository insurancePolicyRepository;

	@Autowired
	private ClientService clientService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public InsurancePolicyDto createNewInsurancePolicy(Integer clientId, InsurancePolicyDto insurancePolicy) {
		Client client = modelMapper.map(clientService.findById(clientId), Client.class);
		InsurancePolicy policy = modelMapper.map(insurancePolicy, InsurancePolicy.class);
		policy.setClient(client);
		InsurancePolicy savedPolicy = insurancePolicyRepository.save(policy);
		return modelMapper.map(savedPolicy, InsurancePolicyDto.class);
	}

	@Transactional
	@Override
	public InsurancePolicyDto getById(Integer insuId) {
		 InsurancePolicy policy = insurancePolicyRepository.findById(insuId)
				.orElseThrow(() -> new ResourceNotFoundException("Insurance Policy", "policy ", "" + insuId));
		return modelMapper.map(policy, InsurancePolicyDto.class);
	}

	@Override
	public InsurancePolicyDto updateInsurancePolcy(InsurancePolicyDto insurancePolicy, Integer insuId) {
		insurancePolicyRepository.findById(insuId)
				.orElseThrow(() -> new ResourceNotFoundException("Insurance Policy", "policy ", "" + insuId));
		InsurancePolicy newInsurance = modelMapper.map(insurancePolicy, InsurancePolicy.class);
		newInsurance.setId(insuId);
		 InsurancePolicy policy = insurancePolicyRepository.save(newInsurance);
		 return modelMapper.map(policy, InsurancePolicyDto.class);
	}

	@Override
	public String deleteInsurancePolicy(Integer insuId) {
		InsurancePolicy insurancePolicy = insurancePolicyRepository.findById(insuId)
				.orElseThrow(() -> new ResourceNotFoundException("Insurance Policy", "policy ", "" + insuId));
		insurancePolicyRepository.delete(insurancePolicy);
		return "Insurance policy deleted successfully...";
	}

	@Override
	public List<InsurancePolicyDto> getAllInsurancePolicy() {
		List<InsurancePolicy> policies = insurancePolicyRepository.findAll();
		return policies.stream().map(policy-> modelMapper.map(policy, InsurancePolicyDto.class)).collect(Collectors.toList());
	}

	@Override
	public InsurancePolicyDto assignPolicyWithUser(Integer clientId, Integer policyId) {
		Client client =modelMapper.map(clientService.findById(clientId),Client.class);
		InsurancePolicyDto policy = modelMapper.map(getById(policyId),InsurancePolicyDto.class);
//		policy.setClient(client);
		 InsurancePolicyDto updateInsurancePolcy = updateInsurancePolcy(policy, policyId);
		 return modelMapper.map(updateInsurancePolcy, InsurancePolicyDto.class);
	}

	@Override
	public List<InsurancePolicy> getAllClaimsByPolicyNumber(Integer policyId) {
		insurancePolicyRepository.findById(policyId).orElseThrow(()-> new ResourceNotFoundException(null));
		return null;
	}

}

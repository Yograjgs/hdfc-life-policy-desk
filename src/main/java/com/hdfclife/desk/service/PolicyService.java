package com.hdfclife.desk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hdfclife.desk.exception.DuplicatePolicyException;
import com.hdfclife.desk.exception.PolicyNotFoundException;
import com.hdfclife.desk.model.Policy;
import com.hdfclife.desk.store.PolicyStore;


@Service

public class PolicyService {


    private final PolicyStore policyStore;

    public PolicyService(PolicyStore policyStore) {
        this.policyStore = policyStore;
    }

    public List<Policy> getPolicies(String status, String type) {
        return policyStore.findAll().stream()
                .filter(p -> status == null || p.getStatus().equalsIgnoreCase(status))
                .filter(p -> type == null || p.getType().equalsIgnoreCase(type))
                .toList();
    }

    public Policy getPolicyByNo(String policyNo) {
        return policyStore.findByPolicyNo(policyNo)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found: " + policyNo));
    }

    public Policy createPolicy(Policy policy) {
        if (policyStore.findByPolicyNo(policy.getPolicyNo()).isPresent()) {
            throw new DuplicatePolicyException("Policy number already exists: " + policy.getPolicyNo());
        }
        policyStore.add(policy);
        return policy;
    }

    public Policy updatePolicy(String policyNo, Policy policyDetails) {
        Policy existing = getPolicyByNo(policyNo);
        existing.setCustomer(policyDetails.getCustomer());
        existing.setType(policyDetails.getType());
        existing.setBasePremium(policyDetails.getBasePremium());
        existing.setStatus(policyDetails.getStatus());
        policyStore.update(existing);
        return existing;
    }

    public void deletePolicy(String policyNo) {
        if (!policyStore.delete(policyNo)) {
            throw new PolicyNotFoundException("Policy not found: " + policyNo);
        }
    }

    public long countActivePolicies() {
        return policyStore.findAll().stream()
                .filter(p -> "Active".equalsIgnoreCase(p.getStatus()))
                .count();
    }

    public long countTermPolicies() {
        return policyStore.findAll().stream()
                .filter(p -> "TERM".equalsIgnoreCase(p.getType()))
                .count();
    }

    public long countUniqueCustomers() {
        return policyStore.findAll().stream()
                .map(Policy::getCustomer)
                .distinct()
                .count();
    }

    public PolicyStore getStore() {
        return this.policyStore;
    }

}
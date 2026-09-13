package com.hdfclife.desk.store;

import java.util.List;
import java.util.Optional;

import com.hdfclife.desk.model.Claim;
import com.hdfclife.desk.model.Policy;

public interface PolicyStore {
    void add(Policy policy);
    List<Policy> findAll();
    Optional<Policy> findByPolicyNo(String policyNo);
    void update(Policy policy);
    boolean delete(String policyNo);
    long count();

    void addClaim(Claim claim);
    Optional<Claim> findClaimByNo(String claimNo);
    List<Claim> findClaimsByPolicyNo(String policyNo);
    long claimCount();

}
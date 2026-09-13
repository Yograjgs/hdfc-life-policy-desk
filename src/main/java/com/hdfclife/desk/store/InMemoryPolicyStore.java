package com.hdfclife.desk.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.hdfclife.desk.model.Claim;
import com.hdfclife.desk.model.Policy;
import java.util.stream.Collectors;

@Repository

public class InMemoryPolicyStore implements com.hdfclife.desk.store.PolicyStore {



    private final List<Policy> policies = new ArrayList<>();
    private final List<Claim> claims = new ArrayList<>();


    public synchronized void add(Policy policy) {
        policies.add(policy);
    }

    @Override
    public synchronized List<Policy> findAll() {
        return new ArrayList<>(policies);
    }

    @Override
    public synchronized Optional<Policy> findByPolicyNo(String policyNo) {
        return policies.stream()
                .filter(p -> p.getPolicyNo().equalsIgnoreCase(policyNo))
                .findFirst();
    }

    @Override
    public synchronized void update(Policy updatedPolicy) {
        for (int i = 0; i < policies.size(); i++) {
            if (policies.get(i).getPolicyNo().equalsIgnoreCase(updatedPolicy.getPolicyNo())) {
                policies.set(i, updatedPolicy);
                return;
            }
        }
    }

    @Override
    public synchronized boolean delete(String policyNo) {
        return policies.removeIf(p -> p.getPolicyNo().equalsIgnoreCase(policyNo));
    }

    @Override
    public synchronized long count() {
        return policies.size();
    }

    @Override
    public synchronized void addClaim(Claim claim) {
        claims.add(claim);
    }

    @Override
    public synchronized Optional<Claim> findClaimByNo(String claimNo) {
        return claims.stream()
                .filter(c -> c.getClaimNo().equalsIgnoreCase(claimNo))
                .findFirst();
    }

    @Override
    public synchronized List<Claim> findClaimsByPolicyNo(String policyNo) {
        return claims.stream()
                .filter(c -> c.getPolicyNo().equalsIgnoreCase(policyNo))
                .toList();
    }

    @Override
    public synchronized long claimCount() {
        return claims.size();
    }

}
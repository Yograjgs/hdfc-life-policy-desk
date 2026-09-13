package com.hdfclife.desk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hdfclife.desk.config.HdfcProperties;
import com.hdfclife.desk.exception.ClaimNotFoundException;
import com.hdfclife.desk.exception.InvalidClaimException;
import com.hdfclife.desk.model.Claim;
import com.hdfclife.desk.store.PolicyStore;


@Service

public class ClaimService {



    private final PolicyStore policyStore;
    private final PolicyService policyService;
    private final HdfcProperties hdfcProperties;

    public ClaimService(PolicyStore policyStore, PolicyService policyService, HdfcProperties hdfcProperties) {
        this.policyStore = policyStore;
        this.policyService = policyService;
        this.hdfcProperties = hdfcProperties;
    }

    public Claim fileClaim(Claim claim) {
        // Validates policy presence
        policyService.getPolicyByNo(claim.getPolicyNo());

        if (claim.getClaimAmount() <= 0 || claim.getClaimAmount() > hdfcProperties.getMaxClaimAmount()) {
            throw new InvalidClaimException("Claim amount must be > 0 and <= " + hdfcProperties.getMaxClaimAmount());
        }

        long nextId = policyStore.claimCount() + 1;
        String claimNo = String.format("CLM-%02d", nextId);
        claim.setClaimNo(claimNo);
        claim.setStatus("SUBMITTED");

        policyStore.addClaim(claim);
        return claim;
    }

    public Claim getClaimByNo(String claimNo) {
        return policyStore.findClaimByNo(claimNo)
                .orElseThrow(() -> new ClaimNotFoundException("Claim not found: " + claimNo));
    }

    public List<Claim> getClaimsForPolicy(String policyNo) {
        policyService.getPolicyByNo(policyNo);
        return policyStore.findClaimsByPolicyNo(policyNo);
    }

}
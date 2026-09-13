package com.hdfclife.desk.web;

import com.hdfclife.desk.model.Claim;
import com.hdfclife.desk.model.Policy;
import com.hdfclife.desk.service.ClaimService;
import com.hdfclife.desk.service.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;



@RestController
@RequestMapping("/api/policies")
@Tag(name = "Policy Management", description = "Endpoints for managing HDFC Life policies")

public class PolicyController {



    private final PolicyService policyService;
    private final ClaimService claimService;

    public PolicyController(PolicyService policyService, ClaimService claimService) {
        this.policyService = policyService;
        this.claimService = claimService;
    }

    @GetMapping
    @Operation(summary = "Get all policies or filter by status and type")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public ResponseEntity<List<Policy>> getAllPolicies(
            @Parameter(description = "Filter by status") @RequestParam(required = false) String status,
            @Parameter(description = "Filter by policy type") @RequestParam(required = false) String type) {
        return ResponseEntity.ok(policyService.getPolicies(status, type));
    }

    @GetMapping("/{policyNo}")
    @Operation(summary = "Get policy by policy number")
    @ApiResponse(responseCode = "200", description = "Policy found")
    @ApiResponse(responseCode = "404", description = "Policy not found")
    public ResponseEntity<Policy> getPolicyByNo(@PathVariable String policyNo) {
        return ResponseEntity.ok(policyService.getPolicyByNo(policyNo));
    }

    @PostMapping
    @Operation(summary = "Create a new policy")
    @ApiResponse(responseCode = "201", description = "Policy created")
    @ApiResponse(responseCode = "409", description = "Duplicate policy number")
    public ResponseEntity<Policy> createPolicy(@Valid @RequestBody Policy policy) {
        Policy created = policyService.createPolicy(policy);
        return ResponseEntity.created(URI.create("/api/policies/" + created.getPolicyNo())).body(created);
    }

    @PutMapping("/{policyNo}")
    @Operation(summary = "Update an existing policy")
    @ApiResponse(responseCode = "200", description = "Policy updated")
    @ApiResponse(responseCode = "404", description = "Policy not found")
    public ResponseEntity<Policy> updatePolicy(@PathVariable String policyNo, @Valid @RequestBody Policy policy) {
        policy.setPolicyNo(policyNo);
        return ResponseEntity.ok(policyService.updatePolicy(policyNo, policy));
    }

    @DeleteMapping("/{policyNo}")
    @Operation(summary = "Delete policy")
    @ApiResponse(responseCode = "204", description = "Policy deleted")
    @ApiResponse(responseCode = "404", description = "Policy not found")
    public ResponseEntity<Void> deletePolicy(@PathVariable String policyNo) {
        policyService.deletePolicy(policyNo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{policyNo}/claims")
    @Operation(summary = "Get claims for a specific policy")
    @ApiResponse(responseCode = "200", description = "Claims retrieved")
    @ApiResponse(responseCode = "404", description = "Policy not found")
    public ResponseEntity<List<Claim>> getClaimsForPolicy(@PathVariable String policyNo) {
        return ResponseEntity.ok(claimService.getClaimsForPolicy(policyNo));
    }

}
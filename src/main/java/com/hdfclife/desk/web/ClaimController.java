package com.hdfclife.desk.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hdfclife.desk.model.Claim;
import com.hdfclife.desk.service.ClaimService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/claims")
@Tag(name = "Claim Management", description = "Endpoints for submitting and viewing policy claims")
public class ClaimController {


    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @PostMapping
    @Operation(summary = "File a new claim")
    @ApiResponse(responseCode = "201", description = "Claim created")
    @ApiResponse(responseCode = "400", description = "Invalid claim amount")
    @ApiResponse(responseCode = "404", description = "Policy not found")
    public ResponseEntity<Claim> fileClaim(@Valid @RequestBody Claim claim) {
        Claim created = claimService.fileClaim(claim);
        return ResponseEntity.created(URI.create("/api/claims/" + created.getClaimNo())).body(created);
    }

    @GetMapping("/{claimNo}")
    @Operation(summary = "Get claim by claim number")
    @ApiResponse(responseCode = "200", description = "Claim found")
    @ApiResponse(responseCode = "404", description = "Claim not found")
    public ResponseEntity<Claim> getClaimByNo(@PathVariable String claimNo) {
        return ResponseEntity.ok(claimService.getClaimByNo(claimNo));
    }

}
package com.hdfclife.desk.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Claim {
    private String claimNo;
    @NotBlank
    private String policyNo;
    @Min(1)
    private int claimAmount;
    @NotNull
    private Urgency urgency;
    private String status = "SUBMITTED";

    public Claim() {}

    public Claim(String claimNo, String policyNo, int claimAmount, Urgency urgency, String status) {
        this.claimNo = claimNo;
        this.policyNo = policyNo;
        this.claimAmount = claimAmount;
        this.urgency = urgency;
        this.status = status;
    }

    public String getClaimNo() { return claimNo; }
    public void setClaimNo(String claimNo) { this.claimNo = claimNo; }
    public String getPolicyNo() { return policyNo; }
    public void setPolicyNo(String policyNo) { this.policyNo = policyNo; }
    public int getClaimAmount() { return claimAmount; }
    public void setClaimAmount(int claimAmount) { this.claimAmount = claimAmount; }
    public Urgency getUrgency() { return urgency; }
    public void setUrgency(Urgency urgency) { this.urgency = urgency; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

}
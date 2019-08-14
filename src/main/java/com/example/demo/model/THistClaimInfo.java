package com.example.demo.model;

import java.math.BigDecimal;
import java.util.Date;

public class THistClaimInfo {
    private Long pkTHistClaimId;

    private String claimsNo;

    private String policyNo;

    private Short customerNo;

    private Short businessNo;

    private Short branchNo;

    private String requestedDate;

    private BigDecimal requestedAmount;

    private BigDecimal realpayAmount;

    private Date jieanDate;

    private String memberPolicyno;

    private Short gender;

    private Short idType;

    private String idCard;

    private Date claimDate;

    private String claimReason;

    private String claimAddr;

    private String claimPass;

    private String claimResult;

    private String claimResultDesc;

    private Date receptionDate;

    private Date archiveDate;

    private Short claimStatus;

    private Short returnStatus;

    private Date returnDate;

    private String customerClaimsNo;

    private String customerBatchNo;

    private String batchNo;

    private String icdCode;

    private String icdName;

    private String reportInsurResu;

    private Date importDate;

    private String imageNo;

    private String rmark;

    private String claimUserName;

    private String bankName;

    private String bankAccount;

    private String bankAccountName;

    private String openingBankAddress;

    private Short paymentMethod;

    private String publicNetwork;

    private String bankCode;

    private String bankProvince;

    private String bankCity;

    private String auditOpinion;

    private Short exportStatus;

    private Date exportDate;

    private Short isnotice;

    private String claimUserId;

    private Short ykReturnStatus;

    private String memberCustomerNo;

    private String gmCustomerNo;

    private String accidentReason;

    public Long getPkTHistClaimId() {
        return pkTHistClaimId;
    }

    public void setPkTHistClaimId(Long pkTHistClaimId) {
        this.pkTHistClaimId = pkTHistClaimId;
    }

    public String getClaimsNo() {
        return claimsNo;
    }

    public void setClaimsNo(String claimsNo) {
        this.claimsNo = claimsNo == null ? null : claimsNo.trim();
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo == null ? null : policyNo.trim();
    }

    public Short getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Short customerNo) {
        this.customerNo = customerNo;
    }

    public Short getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(Short businessNo) {
        this.businessNo = businessNo;
    }

    public Short getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(Short branchNo) {
        this.branchNo = branchNo;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate == null ? null : requestedDate.trim();
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public BigDecimal getRealpayAmount() {
        return realpayAmount;
    }

    public void setRealpayAmount(BigDecimal realpayAmount) {
        this.realpayAmount = realpayAmount;
    }

    public Date getJieanDate() {
        return jieanDate;
    }

    public void setJieanDate(Date jieanDate) {
        this.jieanDate = jieanDate;
    }

    public String getMemberPolicyno() {
        return memberPolicyno;
    }

    public void setMemberPolicyno(String memberPolicyno) {
        this.memberPolicyno = memberPolicyno == null ? null : memberPolicyno.trim();
    }

    public Short getGender() {
        return gender;
    }

    public void setGender(Short gender) {
        this.gender = gender;
    }

    public Short getIdType() {
        return idType;
    }

    public void setIdType(Short idType) {
        this.idType = idType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public String getClaimReason() {
        return claimReason;
    }

    public void setClaimReason(String claimReason) {
        this.claimReason = claimReason == null ? null : claimReason.trim();
    }

    public String getClaimAddr() {
        return claimAddr;
    }

    public void setClaimAddr(String claimAddr) {
        this.claimAddr = claimAddr == null ? null : claimAddr.trim();
    }

    public String getClaimPass() {
        return claimPass;
    }

    public void setClaimPass(String claimPass) {
        this.claimPass = claimPass == null ? null : claimPass.trim();
    }

    public String getClaimResult() {
        return claimResult;
    }

    public void setClaimResult(String claimResult) {
        this.claimResult = claimResult == null ? null : claimResult.trim();
    }

    public String getClaimResultDesc() {
        return claimResultDesc;
    }

    public void setClaimResultDesc(String claimResultDesc) {
        this.claimResultDesc = claimResultDesc == null ? null : claimResultDesc.trim();
    }

    public Date getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(Date receptionDate) {
        this.receptionDate = receptionDate;
    }

    public Date getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(Date archiveDate) {
        this.archiveDate = archiveDate;
    }

    public Short getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(Short claimStatus) {
        this.claimStatus = claimStatus;
    }

    public Short getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Short returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getCustomerClaimsNo() {
        return customerClaimsNo;
    }

    public void setCustomerClaimsNo(String customerClaimsNo) {
        this.customerClaimsNo = customerClaimsNo == null ? null : customerClaimsNo.trim();
    }

    public String getCustomerBatchNo() {
        return customerBatchNo;
    }

    public void setCustomerBatchNo(String customerBatchNo) {
        this.customerBatchNo = customerBatchNo == null ? null : customerBatchNo.trim();
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public String getIcdCode() {
        return icdCode;
    }

    public void setIcdCode(String icdCode) {
        this.icdCode = icdCode == null ? null : icdCode.trim();
    }

    public String getIcdName() {
        return icdName;
    }

    public void setIcdName(String icdName) {
        this.icdName = icdName == null ? null : icdName.trim();
    }

    public String getReportInsurResu() {
        return reportInsurResu;
    }

    public void setReportInsurResu(String reportInsurResu) {
        this.reportInsurResu = reportInsurResu == null ? null : reportInsurResu.trim();
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public String getImageNo() {
        return imageNo;
    }

    public void setImageNo(String imageNo) {
        this.imageNo = imageNo == null ? null : imageNo.trim();
    }

    public String getRmark() {
        return rmark;
    }

    public void setRmark(String rmark) {
        this.rmark = rmark == null ? null : rmark.trim();
    }

    public String getClaimUserName() {
        return claimUserName;
    }

    public void setClaimUserName(String claimUserName) {
        this.claimUserName = claimUserName == null ? null : claimUserName.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName == null ? null : bankAccountName.trim();
    }

    public String getOpeningBankAddress() {
        return openingBankAddress;
    }

    public void setOpeningBankAddress(String openingBankAddress) {
        this.openingBankAddress = openingBankAddress == null ? null : openingBankAddress.trim();
    }

    public Short getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Short paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPublicNetwork() {
        return publicNetwork;
    }

    public void setPublicNetwork(String publicNetwork) {
        this.publicNetwork = publicNetwork == null ? null : publicNetwork.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince == null ? null : bankProvince.trim();
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity == null ? null : bankCity.trim();
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion == null ? null : auditOpinion.trim();
    }

    public Short getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(Short exportStatus) {
        this.exportStatus = exportStatus;
    }

    public Date getExportDate() {
        return exportDate;
    }

    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
    }

    public Short getIsnotice() {
        return isnotice;
    }

    public void setIsnotice(Short isnotice) {
        this.isnotice = isnotice;
    }

    public String getClaimUserId() {
        return claimUserId;
    }

    public void setClaimUserId(String claimUserId) {
        this.claimUserId = claimUserId == null ? null : claimUserId.trim();
    }

    public Short getYkReturnStatus() {
        return ykReturnStatus;
    }

    public void setYkReturnStatus(Short ykReturnStatus) {
        this.ykReturnStatus = ykReturnStatus;
    }

    public String getMemberCustomerNo() {
        return memberCustomerNo;
    }

    public void setMemberCustomerNo(String memberCustomerNo) {
        this.memberCustomerNo = memberCustomerNo == null ? null : memberCustomerNo.trim();
    }

    public String getGmCustomerNo() {
        return gmCustomerNo;
    }

    public void setGmCustomerNo(String gmCustomerNo) {
        this.gmCustomerNo = gmCustomerNo == null ? null : gmCustomerNo.trim();
    }

    public String getAccidentReason() {
        return accidentReason;
    }

    public void setAccidentReason(String accidentReason) {
        this.accidentReason = accidentReason == null ? null : accidentReason.trim();
    }
}
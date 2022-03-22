
package com.platform.pojo.contracts.contract;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Contract {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("createdAt")
    @Expose
    private Long createdAt;
    @SerializedName("updatedAt")
    @Expose
    private Long updatedAt;
    @SerializedName("draft")
    @Expose
    private Boolean draft;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("supplierNumber")
    @Expose
    private String supplierNumber;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("date")
    @Expose
    private Long date;
    @SerializedName("endDate")
    @Expose
    private Long endDate;
    @SerializedName("saleStartDate")
    @Expose
    private Long saleStartDate;
    @SerializedName("withdrawPeriod")
    @Expose
    private Integer withdrawPeriod;
    @SerializedName("withdrawDate")
    @Expose
    private Long withdrawDate;
    @SerializedName("supplier")
    @Expose
    private Supplier supplier;
    @SerializedName("recipient")
    @Expose
    private Recipient recipient;
    @SerializedName("recipientAddress")
    @Expose
    private RecipientAddress recipientAddress;
    @SerializedName("contact")
    @Expose
    private Contact contact;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("overseer")
    @Expose
    private Overseer overseer;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("size")
    @Expose
    private Double size;
    @SerializedName("currentAmount")
    @Expose
    private Double currentAmount;
    @SerializedName("endOfContractAmount")
    @Expose
    private Double endOfContractAmount;
    @SerializedName("dateOfPriceList")
    @Expose
    private Long dateOfPriceList;
    @SerializedName("energiaPriceList")
    @Expose
    private EnergiaPriceList energiaPriceList;
    @SerializedName("overseerSettlements")
    @Expose
    private List<Object> overseerSettlements = null;
    @SerializedName("supplierSettlements")
    @Expose
    private List<Object> supplierSettlements = null;
    @SerializedName("contractor")
    @Expose
    private Contractor contractor;
    @SerializedName("contextNo")
    @Expose
    private String contextNo;
    @SerializedName("entityType")
    @Expose
    private String entityType;
    @SerializedName("contextable")
    @Expose
    private Boolean contextable;
    @SerializedName("entityDescription")
    @Expose
    private String entityDescription;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getDraft() {
        return draft;
    }

    public void setDraft(Boolean draft) {
        this.draft = draft;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Long getSaleStartDate() {
        return saleStartDate;
    }

    public void setSaleStartDate(Long saleStartDate) {
        this.saleStartDate = saleStartDate;
    }

    public Integer getWithdrawPeriod() {
        return withdrawPeriod;
    }

    public void setWithdrawPeriod(Integer withdrawPeriod) {
        this.withdrawPeriod = withdrawPeriod;
    }

    public Long getWithdrawDate() {
        return withdrawDate;
    }

    public void setWithdrawDate(Long withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public RecipientAddress getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(RecipientAddress recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Overseer getOverseer() {
        return overseer;
    }

    public void setOverseer(Overseer overseer) {
        this.overseer = overseer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Double getEndOfContractAmount() {
        return endOfContractAmount;
    }

    public void setEndOfContractAmount(Double endOfContractAmount) {
        this.endOfContractAmount = endOfContractAmount;
    }

    public Long getDateOfPriceList() {
        return dateOfPriceList;
    }

    public void setDateOfPriceList(Long dateOfPriceList) {
        this.dateOfPriceList = dateOfPriceList;
    }

    public EnergiaPriceList getEnergiaPriceList() {
        return energiaPriceList;
    }

    public void setEnergiaPriceList(EnergiaPriceList energiaPriceList) {
        this.energiaPriceList = energiaPriceList;
    }

    public List<Object> getOverseerSettlements() {
        return overseerSettlements;
    }

    public void setOverseerSettlements(List<Object> overseerSettlements) {
        this.overseerSettlements = overseerSettlements;
    }

    public List<Object> getSupplierSettlements() {
        return supplierSettlements;
    }

    public void setSupplierSettlements(List<Object> supplierSettlements) {
        this.supplierSettlements = supplierSettlements;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public String getContextNo() {
        return contextNo;
    }

    public void setContextNo(String contextNo) {
        this.contextNo = contextNo;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Boolean getContextable() {
        return contextable;
    }

    public void setContextable(Boolean contextable) {
        this.contextable = contextable;
    }

    public String getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
    }

}


package com.platform.pojo.costInvoices;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Result {

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
    @SerializedName("issueDate")
    @Expose
    private Long issueDate;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("paid")
    @Expose
    private Boolean paid;
    @SerializedName("contractor")
    @Expose
    private Contractor contractor;
    @SerializedName("paymentTerm")
    @Expose
    private Long paymentTerm;
    @SerializedName("paymentDate")
    @Expose
    private Object paymentDate;
    @SerializedName("currency")
    @Expose
    private Currency currency;
    @SerializedName("netTotal")
    @Expose
    private Double netTotal;
    @SerializedName("vatTotal")
    @Expose
    private Double vatTotal;
    @SerializedName("grossTotal")
    @Expose
    private Double grossTotal;
    @SerializedName("comments")
    @Expose
    private Object comments;
    @SerializedName("foreignNumber")
    @Expose
    private String foreignNumber;
    @SerializedName("sum")
    @Expose
    private Double sum;
    @SerializedName("entityDescription")
    @Expose
    private String entityDescription;
    @SerializedName("contextNo")
    @Expose
    private String contextNo;
    @SerializedName("entityType")
    @Expose
    private String entityType;
    @SerializedName("contextable")
    @Expose
    private Boolean contextable;

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

    public Long getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Long issueDate) {
        this.issueDate = issueDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public Long getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(Long paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public Object getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Object paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(Double netTotal) {
        this.netTotal = netTotal;
    }

    public Double getVatTotal() {
        return vatTotal;
    }

    public void setVatTotal(Double vatTotal) {
        this.vatTotal = vatTotal;
    }

    public Double getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(Double grossTotal) {
        this.grossTotal = grossTotal;
    }

    public Object getComments() {
        return comments;
    }

    public void setComments(Object comments) {
        this.comments = comments;
    }

    public String getForeignNumber() {
        return foreignNumber;
    }

    public void setForeignNumber(String foreignNumber) {
        this.foreignNumber = foreignNumber;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
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

}

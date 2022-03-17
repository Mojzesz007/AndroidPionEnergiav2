
package com.platform.pojo.costInvoice.create;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CostInvoiceCreate {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("createdAt")
    @Expose
    private Long createdAt;
    @SerializedName("updatedAt")
    @Expose
    private Object updatedAt;
    @SerializedName("draft")
    @Expose
    private Boolean draft;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("number")
    @Expose
    private Object number;
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
    private Object contractor;
    @SerializedName("paymentTerm")
    @Expose
    private Object paymentTerm;
    @SerializedName("paymentDate")
    @Expose
    private Object paymentDate;
    @SerializedName("currency")
    @Expose
    private Currency currency;
    @SerializedName("netTotal")
    @Expose
    private Object netTotal;
    @SerializedName("vatTotal")
    @Expose
    private Object vatTotal;
    @SerializedName("grossTotal")
    @Expose
    private Object grossTotal;
    @SerializedName("comments")
    @Expose
    private Object comments;
    @SerializedName("foreignNumber")
    @Expose
    private Object foreignNumber;
    @SerializedName("sum")
    @Expose
    private Integer sum;
    @SerializedName("contextNo")
    @Expose
    private Object contextNo;
    @SerializedName("entityDescription")
    @Expose
    private Object entityDescription;
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

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
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

    public Object getNumber() {
        return number;
    }

    public void setNumber(Object number) {
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

    public Object getContractor() {
        return contractor;
    }

    public void setContractor(Object contractor) {
        this.contractor = contractor;
    }

    public Object getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(Object paymentTerm) {
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

    public Object getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(Object netTotal) {
        this.netTotal = netTotal;
    }

    public Object getVatTotal() {
        return vatTotal;
    }

    public void setVatTotal(Object vatTotal) {
        this.vatTotal = vatTotal;
    }

    public Object getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(Object grossTotal) {
        this.grossTotal = grossTotal;
    }

    public Object getComments() {
        return comments;
    }

    public void setComments(Object comments) {
        this.comments = comments;
    }

    public Object getForeignNumber() {
        return foreignNumber;
    }

    public void setForeignNumber(Object foreignNumber) {
        this.foreignNumber = foreignNumber;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Object getContextNo() {
        return contextNo;
    }

    public void setContextNo(Object contextNo) {
        this.contextNo = contextNo;
    }

    public Object getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(Object entityDescription) {
        this.entityDescription = entityDescription;
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

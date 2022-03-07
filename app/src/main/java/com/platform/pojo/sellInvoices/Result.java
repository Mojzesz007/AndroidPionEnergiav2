
package com.platform.pojo.sellInvoices;

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
    @SerializedName("saleDate")
    @Expose
    private Long saleDate;
    @SerializedName("paymentForm")
    @Expose
    private PaymentForm paymentForm;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("recipientContractor")
    @Expose
    private Object recipientContractor;
    @SerializedName("items")
    @Expose
    private Object items;
    @SerializedName("order")
    @Expose
    private Object order;
    @SerializedName("hours")
    @Expose
    private Integer hours;
    @SerializedName("issuer")
    @Expose
    private String issuer;
    @SerializedName("receiver")
    @Expose
    private Object receiver;
    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("contractorName")
    @Expose
    private String contractorName;
    @SerializedName("contractorNip")
    @Expose
    private String contractorNip;
    @SerializedName("contractorRegon")
    @Expose
    private String contractorRegon;
    @SerializedName("contractorStreet")
    @Expose
    private String contractorStreet;
    @SerializedName("contractorStreetNo")
    @Expose
    private String contractorStreetNo;
    @SerializedName("contractorFlatNo")
    @Expose
    private Object contractorFlatNo;
    @SerializedName("contractorCity")
    @Expose
    private String contractorCity;
    @SerializedName("contractorPostalcode")
    @Expose
    private String contractorPostalcode;
    @SerializedName("contractorEInvoiceEmail")
    @Expose
    private Object contractorEInvoiceEmail;
    @SerializedName("recipientContractorName")
    @Expose
    private Object recipientContractorName;
    @SerializedName("recipientContractorNip")
    @Expose
    private Object recipientContractorNip;
    @SerializedName("recipientContractorRegon")
    @Expose
    private Object recipientContractorRegon;
    @SerializedName("recipientContractorStreet")
    @Expose
    private Object recipientContractorStreet;
    @SerializedName("recipientContractorStreetNo")
    @Expose
    private Object recipientContractorStreetNo;
    @SerializedName("recipientContractorFlatNo")
    @Expose
    private Object recipientContractorFlatNo;
    @SerializedName("recipientContractorCity")
    @Expose
    private Object recipientContractorCity;
    @SerializedName("recipientContractorPostalcode")
    @Expose
    private Object recipientContractorPostalcode;
    @SerializedName("gtus")
    @Expose
    private Object gtus;
    @SerializedName("jpkv7")
    @Expose
    private Object jpkv7;
    @SerializedName("payments")
    @Expose
    private Object payments;
    @SerializedName("sum")
    @Expose
    private Double sum;
    @SerializedName("deliveryType")
    @Expose
    private String deliveryType;
    @SerializedName("sendInvoiceDate")
    @Expose
    private Object sendInvoiceDate;
    @SerializedName("linkedInvoices")
    @Expose
    private Object linkedInvoices;
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

    public Long getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Long saleDate) {
        this.saleDate = saleDate;
    }

    public PaymentForm getPaymentForm() {
        return paymentForm;
    }

    public void setPaymentForm(PaymentForm paymentForm) {
        this.paymentForm = paymentForm;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Object getRecipientContractor() {
        return recipientContractor;
    }

    public void setRecipientContractor(Object recipientContractor) {
        this.recipientContractor = recipientContractor;
    }

    public Object getItems() {
        return items;
    }

    public void setItems(Object items) {
        this.items = items;
    }

    public Object getOrder() {
        return order;
    }

    public void setOrder(Object order) {
        this.order = order;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Object getReceiver() {
        return receiver;
    }

    public void setReceiver(Object receiver) {
        this.receiver = receiver;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public String getContractorNip() {
        return contractorNip;
    }

    public void setContractorNip(String contractorNip) {
        this.contractorNip = contractorNip;
    }

    public String getContractorRegon() {
        return contractorRegon;
    }

    public void setContractorRegon(String contractorRegon) {
        this.contractorRegon = contractorRegon;
    }

    public String getContractorStreet() {
        return contractorStreet;
    }

    public void setContractorStreet(String contractorStreet) {
        this.contractorStreet = contractorStreet;
    }

    public String getContractorStreetNo() {
        return contractorStreetNo;
    }

    public void setContractorStreetNo(String contractorStreetNo) {
        this.contractorStreetNo = contractorStreetNo;
    }

    public Object getContractorFlatNo() {
        return contractorFlatNo;
    }

    public void setContractorFlatNo(Object contractorFlatNo) {
        this.contractorFlatNo = contractorFlatNo;
    }

    public String getContractorCity() {
        return contractorCity;
    }

    public void setContractorCity(String contractorCity) {
        this.contractorCity = contractorCity;
    }

    public String getContractorPostalcode() {
        return contractorPostalcode;
    }

    public void setContractorPostalcode(String contractorPostalcode) {
        this.contractorPostalcode = contractorPostalcode;
    }

    public Object getContractorEInvoiceEmail() {
        return contractorEInvoiceEmail;
    }

    public void setContractorEInvoiceEmail(Object contractorEInvoiceEmail) {
        this.contractorEInvoiceEmail = contractorEInvoiceEmail;
    }

    public Object getRecipientContractorName() {
        return recipientContractorName;
    }

    public void setRecipientContractorName(Object recipientContractorName) {
        this.recipientContractorName = recipientContractorName;
    }

    public Object getRecipientContractorNip() {
        return recipientContractorNip;
    }

    public void setRecipientContractorNip(Object recipientContractorNip) {
        this.recipientContractorNip = recipientContractorNip;
    }

    public Object getRecipientContractorRegon() {
        return recipientContractorRegon;
    }

    public void setRecipientContractorRegon(Object recipientContractorRegon) {
        this.recipientContractorRegon = recipientContractorRegon;
    }

    public Object getRecipientContractorStreet() {
        return recipientContractorStreet;
    }

    public void setRecipientContractorStreet(Object recipientContractorStreet) {
        this.recipientContractorStreet = recipientContractorStreet;
    }

    public Object getRecipientContractorStreetNo() {
        return recipientContractorStreetNo;
    }

    public void setRecipientContractorStreetNo(Object recipientContractorStreetNo) {
        this.recipientContractorStreetNo = recipientContractorStreetNo;
    }

    public Object getRecipientContractorFlatNo() {
        return recipientContractorFlatNo;
    }

    public void setRecipientContractorFlatNo(Object recipientContractorFlatNo) {
        this.recipientContractorFlatNo = recipientContractorFlatNo;
    }

    public Object getRecipientContractorCity() {
        return recipientContractorCity;
    }

    public void setRecipientContractorCity(Object recipientContractorCity) {
        this.recipientContractorCity = recipientContractorCity;
    }

    public Object getRecipientContractorPostalcode() {
        return recipientContractorPostalcode;
    }

    public void setRecipientContractorPostalcode(Object recipientContractorPostalcode) {
        this.recipientContractorPostalcode = recipientContractorPostalcode;
    }

    public Object getGtus() {
        return gtus;
    }

    public void setGtus(Object gtus) {
        this.gtus = gtus;
    }

    public Object getJpkv7() {
        return jpkv7;
    }

    public void setJpkv7(Object jpkv7) {
        this.jpkv7 = jpkv7;
    }

    public Object getPayments() {
        return payments;
    }

    public void setPayments(Object payments) {
        this.payments = payments;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Object getSendInvoiceDate() {
        return sendInvoiceDate;
    }

    public void setSendInvoiceDate(Object sendInvoiceDate) {
        this.sendInvoiceDate = sendInvoiceDate;
    }

    public Object getLinkedInvoices() {
        return linkedInvoices;
    }

    public void setLinkedInvoices(Object linkedInvoices) {
        this.linkedInvoices = linkedInvoices;
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

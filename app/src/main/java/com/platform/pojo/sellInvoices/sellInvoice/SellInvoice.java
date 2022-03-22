
package com.platform.pojo.sellInvoices.sellInvoice;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class SellInvoice {

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
    private String comments;
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
    private RecipientContractor recipientContractor;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
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
    private String receiver;
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
    private String contractorFlatNo;
    @SerializedName("contractorCity")
    @Expose
    private String contractorCity;
    @SerializedName("contractorPostalcode")
    @Expose
    private String contractorPostalcode;
    @SerializedName("contractorEInvoiceEmail")
    @Expose
    private String contractorEInvoiceEmail;
    @SerializedName("recipientContractorName")
    @Expose
    private String recipientContractorName;
    @SerializedName("recipientContractorNip")
    @Expose
    private String recipientContractorNip;
    @SerializedName("recipientContractorRegon")
    @Expose
    private String recipientContractorRegon;
    @SerializedName("recipientContractorStreet")
    @Expose
    private String recipientContractorStreet;
    @SerializedName("recipientContractorStreetNo")
    @Expose
    private String recipientContractorStreetNo;
    @SerializedName("recipientContractorFlatNo")
    @Expose
    private String recipientContractorFlatNo;
    @SerializedName("recipientContractorCity")
    @Expose
    private String recipientContractorCity;
    @SerializedName("recipientContractorPostalcode")
    @Expose
    private String recipientContractorPostalcode;
    @SerializedName("gtus")
    @Expose
    private List<Gtu> gtus = null;
    @SerializedName("jpkv7")
    @Expose
    private List<Jpkv7> jpkv7 = null;
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
    private Long sendInvoiceDate;
    @SerializedName("linkedInvoices")
    @Expose
    private List<Object> linkedInvoices = null;
    @SerializedName("contextNo")
    @Expose
    private String contextNo;
    @SerializedName("entityType")
    @Expose
    private String entityType;
    @SerializedName("entityDescription")
    @Expose
    private String entityDescription;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
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

    public RecipientContractor getRecipientContractor() {
        return recipientContractor;
    }

    public void setRecipientContractor(RecipientContractor recipientContractor) {
        this.recipientContractor = recipientContractor;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
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

    public String getContractorFlatNo() {
        return contractorFlatNo;
    }

    public void setContractorFlatNo(String contractorFlatNo) {
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

    public String getContractorEInvoiceEmail() {
        return contractorEInvoiceEmail;
    }

    public void setContractorEInvoiceEmail(String contractorEInvoiceEmail) {
        this.contractorEInvoiceEmail = contractorEInvoiceEmail;
    }

    public String getRecipientContractorName() {
        return recipientContractorName;
    }

    public void setRecipientContractorName(String recipientContractorName) {
        this.recipientContractorName = recipientContractorName;
    }

    public String getRecipientContractorNip() {
        return recipientContractorNip;
    }

    public void setRecipientContractorNip(String recipientContractorNip) {
        this.recipientContractorNip = recipientContractorNip;
    }

    public String getRecipientContractorRegon() {
        return recipientContractorRegon;
    }

    public void setRecipientContractorRegon(String recipientContractorRegon) {
        this.recipientContractorRegon = recipientContractorRegon;
    }

    public String getRecipientContractorStreet() {
        return recipientContractorStreet;
    }

    public void setRecipientContractorStreet(String recipientContractorStreet) {
        this.recipientContractorStreet = recipientContractorStreet;
    }

    public String getRecipientContractorStreetNo() {
        return recipientContractorStreetNo;
    }

    public void setRecipientContractorStreetNo(String recipientContractorStreetNo) {
        this.recipientContractorStreetNo = recipientContractorStreetNo;
    }

    public String getRecipientContractorFlatNo() {
        return recipientContractorFlatNo;
    }

    public void setRecipientContractorFlatNo(String recipientContractorFlatNo) {
        this.recipientContractorFlatNo = recipientContractorFlatNo;
    }

    public String getRecipientContractorCity() {
        return recipientContractorCity;
    }

    public void setRecipientContractorCity(String recipientContractorCity) {
        this.recipientContractorCity = recipientContractorCity;
    }

    public String getRecipientContractorPostalcode() {
        return recipientContractorPostalcode;
    }

    public void setRecipientContractorPostalcode(String recipientContractorPostalcode) {
        this.recipientContractorPostalcode = recipientContractorPostalcode;
    }

    public List<Gtu> getGtus() {
        return gtus;
    }

    public void setGtus(List<Gtu> gtus) {
        this.gtus = gtus;
    }

    public List<Jpkv7> getJpkv7() {
        return jpkv7;
    }

    public void setJpkv7(List<Jpkv7> jpkv7) {
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

    public Long getSendInvoiceDate() {
        return sendInvoiceDate;
    }

    public void setSendInvoiceDate(Long sendInvoiceDate) {
        this.sendInvoiceDate = sendInvoiceDate;
    }

    public List<Object> getLinkedInvoices() {
        return linkedInvoices;
    }

    public void setLinkedInvoices(List<Object> linkedInvoices) {
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

    public String getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
    }

    public Boolean getContextable() {
        return contextable;
    }

    public void setContextable(Boolean contextable) {
        this.contextable = contextable;
    }

}


package com.platform.pojo.solarContracts;

import java.util.List;
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
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("date")
    @Expose
    private Long date;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("recipient")
    @Expose
    private Recipient recipient;
    @SerializedName("recipientAddress")
    @Expose
    private RecipientAddress recipientAddress;
    @SerializedName("contact")
    @Expose
    private Contact contact;
    @SerializedName("representatives")
    @Expose
    private Object representatives;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("module")
    @Expose
    private Object module;
    @SerializedName("moduleUnitPower")
    @Expose
    private Double moduleUnitPower;
    @SerializedName("moduleQuantity")
    @Expose
    private Object moduleQuantity;
    @SerializedName("moduleTotalPower")
    @Expose
    private Object moduleTotalPower;
    @SerializedName("inverter")
    @Expose
    private Object inverter;
    @SerializedName("inverterUnitPower")
    @Expose
    private Object inverterUnitPower;
    @SerializedName("inverterQuantity")
    @Expose
    private Object inverterQuantity;
    @SerializedName("inverters")
    @Expose
    private List<Inverter> inverters = null;
    @SerializedName("optimizer")
    @Expose
    private Object optimizer;
    @SerializedName("insuranceOneYear")
    @Expose
    private Boolean insuranceOneYear;
    @SerializedName("oneMeter")
    @Expose
    private Boolean oneMeter;
    @SerializedName("netValue")
    @Expose
    private Double netValue;
    @SerializedName("grossValue")
    @Expose
    private Double grossValue;
    @SerializedName("currency")
    @Expose
    private Currency currency;
    @SerializedName("warrantyPeriod")
    @Expose
    private Double warrantyPeriod;
    @SerializedName("warrantyManufacturer")
    @Expose
    private Double warrantyManufacturer;
    @SerializedName("warrantyPanels")
    @Expose
    private Double warrantyPanels;
    @SerializedName("warrantyInverter")
    @Expose
    private Double warrantyInverter;
    @SerializedName("warrantyPowerLoss")
    @Expose
    private Double warrantyPowerLoss;
    @SerializedName("methodOfInstallation")
    @Expose
    private Object methodOfInstallation;
    @SerializedName("numberOfStrings")
    @Expose
    private Object numberOfStrings;
    @SerializedName("earthResistanceMeasurementResult")
    @Expose
    private Object earthResistanceMeasurementResult;
    @SerializedName("paymentTerms")
    @Expose
    private String paymentTerms;
    @SerializedName("otherProvisions")
    @Expose
    private String otherProvisions;
    @SerializedName("annex")
    @Expose
    private Boolean annex;
    @SerializedName("companyContract")
    @Expose
    private Boolean companyContract;
    @SerializedName("coordinator")
    @Expose
    private Coordinator coordinator;
    @SerializedName("amountOnInvoice")
    @Expose
    private Double amountOnInvoice;
    @SerializedName("externalAmount")
    @Expose
    private Double externalAmount;
    @SerializedName("conventionalPower")
    @Expose
    private Double conventionalPower;
    @SerializedName("numberPPE")
    @Expose
    private String numberPPE;
    @SerializedName("energyMeterNumber")
    @Expose
    private String energyMeterNumber;
    @SerializedName("contractor")
    @Expose
    private Contractor__2 contractor;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public Object getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(Object representatives) {
        this.representatives = representatives;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getModule() {
        return module;
    }

    public void setModule(Object module) {
        this.module = module;
    }

    public Double getModuleUnitPower() {
        return moduleUnitPower;
    }

    public void setModuleUnitPower(Double moduleUnitPower) {
        this.moduleUnitPower = moduleUnitPower;
    }

    public Object getModuleQuantity() {
        return moduleQuantity;
    }

    public void setModuleQuantity(Object moduleQuantity) {
        this.moduleQuantity = moduleQuantity;
    }

    public Object getModuleTotalPower() {
        return moduleTotalPower;
    }

    public void setModuleTotalPower(Object moduleTotalPower) {
        this.moduleTotalPower = moduleTotalPower;
    }

    public Object getInverter() {
        return inverter;
    }

    public void setInverter(Object inverter) {
        this.inverter = inverter;
    }

    public Object getInverterUnitPower() {
        return inverterUnitPower;
    }

    public void setInverterUnitPower(Object inverterUnitPower) {
        this.inverterUnitPower = inverterUnitPower;
    }

    public Object getInverterQuantity() {
        return inverterQuantity;
    }

    public void setInverterQuantity(Object inverterQuantity) {
        this.inverterQuantity = inverterQuantity;
    }

    public List<Inverter> getInverters() {
        return inverters;
    }

    public void setInverters(List<Inverter> inverters) {
        this.inverters = inverters;
    }

    public Object getOptimizer() {
        return optimizer;
    }

    public void setOptimizer(Object optimizer) {
        this.optimizer = optimizer;
    }

    public Boolean getInsuranceOneYear() {
        return insuranceOneYear;
    }

    public void setInsuranceOneYear(Boolean insuranceOneYear) {
        this.insuranceOneYear = insuranceOneYear;
    }

    public Boolean getOneMeter() {
        return oneMeter;
    }

    public void setOneMeter(Boolean oneMeter) {
        this.oneMeter = oneMeter;
    }

    public Double getNetValue() {
        return netValue;
    }

    public void setNetValue(Double netValue) {
        this.netValue = netValue;
    }

    public Double getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(Double grossValue) {
        this.grossValue = grossValue;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(Double warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public Double getWarrantyManufacturer() {
        return warrantyManufacturer;
    }

    public void setWarrantyManufacturer(Double warrantyManufacturer) {
        this.warrantyManufacturer = warrantyManufacturer;
    }

    public Double getWarrantyPanels() {
        return warrantyPanels;
    }

    public void setWarrantyPanels(Double warrantyPanels) {
        this.warrantyPanels = warrantyPanels;
    }

    public Double getWarrantyInverter() {
        return warrantyInverter;
    }

    public void setWarrantyInverter(Double warrantyInverter) {
        this.warrantyInverter = warrantyInverter;
    }

    public Double getWarrantyPowerLoss() {
        return warrantyPowerLoss;
    }

    public void setWarrantyPowerLoss(Double warrantyPowerLoss) {
        this.warrantyPowerLoss = warrantyPowerLoss;
    }

    public Object getMethodOfInstallation() {
        return methodOfInstallation;
    }

    public void setMethodOfInstallation(Object methodOfInstallation) {
        this.methodOfInstallation = methodOfInstallation;
    }

    public Object getNumberOfStrings() {
        return numberOfStrings;
    }

    public void setNumberOfStrings(Object numberOfStrings) {
        this.numberOfStrings = numberOfStrings;
    }

    public Object getEarthResistanceMeasurementResult() {
        return earthResistanceMeasurementResult;
    }

    public void setEarthResistanceMeasurementResult(Object earthResistanceMeasurementResult) {
        this.earthResistanceMeasurementResult = earthResistanceMeasurementResult;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getOtherProvisions() {
        return otherProvisions;
    }

    public void setOtherProvisions(String otherProvisions) {
        this.otherProvisions = otherProvisions;
    }

    public Boolean getAnnex() {
        return annex;
    }

    public void setAnnex(Boolean annex) {
        this.annex = annex;
    }

    public Boolean getCompanyContract() {
        return companyContract;
    }

    public void setCompanyContract(Boolean companyContract) {
        this.companyContract = companyContract;
    }

    public Coordinator getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    public Double getAmountOnInvoice() {
        return amountOnInvoice;
    }

    public void setAmountOnInvoice(Double amountOnInvoice) {
        this.amountOnInvoice = amountOnInvoice;
    }

    public Double getExternalAmount() {
        return externalAmount;
    }

    public void setExternalAmount(Double externalAmount) {
        this.externalAmount = externalAmount;
    }

    public Double getConventionalPower() {
        return conventionalPower;
    }

    public void setConventionalPower(Double conventionalPower) {
        this.conventionalPower = conventionalPower;
    }

    public String getNumberPPE() {
        return numberPPE;
    }

    public void setNumberPPE(String numberPPE) {
        this.numberPPE = numberPPE;
    }

    public String getEnergyMeterNumber() {
        return energyMeterNumber;
    }

    public void setEnergyMeterNumber(String energyMeterNumber) {
        this.energyMeterNumber = energyMeterNumber;
    }

    public Contractor__2 getContractor() {
        return contractor;
    }

    public void setContractor(Contractor__2 contractor) {
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

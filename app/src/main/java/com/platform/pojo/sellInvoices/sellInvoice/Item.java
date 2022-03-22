
package com.platform.pojo.sellInvoices.sellInvoice;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Item {

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
    @SerializedName("type")
    @Expose
    private Object type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("unit")
    @Expose
    private Unit unit;
    @SerializedName("pkwiu")
    @Expose
    private Object pkwiu;
    @SerializedName("quantity")
    @Expose
    private Double quantity;
    @SerializedName("netPrice")
    @Expose
    private Double netPrice;
    @SerializedName("vat")
    @Expose
    private Vat vat;
    @SerializedName("grossPrice")
    @Expose
    private Double grossPrice;
    @SerializedName("netValue")
    @Expose
    private Double netValue;
    @SerializedName("vatValue")
    @Expose
    private Double vatValue;
    @SerializedName("grossValue")
    @Expose
    private Double grossValue;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("netPriceAfterDiscount")
    @Expose
    private Double netPriceAfterDiscount;
    @SerializedName("entityType")
    @Expose
    private String entityType;
    @SerializedName("entityDescription")
    @Expose
    private Object entityDescription;
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

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Object getPkwiu() {
        return pkwiu;
    }

    public void setPkwiu(Object pkwiu) {
        this.pkwiu = pkwiu;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(Double netPrice) {
        this.netPrice = netPrice;
    }

    public Vat getVat() {
        return vat;
    }

    public void setVat(Vat vat) {
        this.vat = vat;
    }

    public Double getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(Double grossPrice) {
        this.grossPrice = grossPrice;
    }

    public Double getNetValue() {
        return netValue;
    }

    public void setNetValue(Double netValue) {
        this.netValue = netValue;
    }

    public Double getVatValue() {
        return vatValue;
    }

    public void setVatValue(Double vatValue) {
        this.vatValue = vatValue;
    }

    public Double getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(Double grossValue) {
        this.grossValue = grossValue;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Double getNetPriceAfterDiscount() {
        return netPriceAfterDiscount;
    }

    public void setNetPriceAfterDiscount(Double netPriceAfterDiscount) {
        this.netPriceAfterDiscount = netPriceAfterDiscount;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Object getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(Object entityDescription) {
        this.entityDescription = entityDescription;
    }

    public Boolean getContextable() {
        return contextable;
    }

    public void setContextable(Boolean contextable) {
        this.contextable = contextable;
    }

}

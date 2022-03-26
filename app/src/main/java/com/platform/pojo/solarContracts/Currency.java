
package com.platform.pojo.solarContracts;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Currency {

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
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("format")
    @Expose
    private Object format;
    @SerializedName("iban")
    @Expose
    private Object iban;
    @SerializedName("bank")
    @Expose
    private Object bank;
    @SerializedName("swiftCode")
    @Expose
    private Object swiftCode;
    @SerializedName("primary")
    @Expose
    private Boolean primary;
    @SerializedName("entityDescription")
    @Expose
    private String entityDescription;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getFormat() {
        return format;
    }

    public void setFormat(Object format) {
        this.format = format;
    }

    public Object getIban() {
        return iban;
    }

    public void setIban(Object iban) {
        this.iban = iban;
    }

    public Object getBank() {
        return bank;
    }

    public void setBank(Object bank) {
        this.bank = bank;
    }

    public Object getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(Object swiftCode) {
        this.swiftCode = swiftCode;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public String getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(String entityDescription) {
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

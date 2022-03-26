
package com.platform.pojo.solarContracts.solarContract;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Inverter {

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
    @SerializedName("inverter")
    @Expose
    private Inverter__1 inverter;
    @SerializedName("inverterUnitPower")
    @Expose
    private Double inverterUnitPower;
    @SerializedName("inverterQuantity")
    @Expose
    private Double inverterQuantity;
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

    public Inverter__1 getInverter() {
        return inverter;
    }

    public void setInverter(Inverter__1 inverter) {
        this.inverter = inverter;
    }

    public Double getInverterUnitPower() {
        return inverterUnitPower;
    }

    public void setInverterUnitPower(Double inverterUnitPower) {
        this.inverterUnitPower = inverterUnitPower;
    }

    public Double getInverterQuantity() {
        return inverterQuantity;
    }

    public void setInverterQuantity(Double inverterQuantity) {
        this.inverterQuantity = inverterQuantity;
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

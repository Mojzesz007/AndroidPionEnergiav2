
package com.platform.pojo.solarContracts.solarContract;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Contact {

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
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("contractor")
    @Expose
    private Contractor contractor;
    @SerializedName("user")
    @Expose
    private Object user;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("officePhone")
    @Expose
    private String officePhone;
    @SerializedName("privatePhone")
    @Expose
    private Object privatePhone;
    @SerializedName("businessPhone")
    @Expose
    private String businessPhone;
    @SerializedName("businessMail")
    @Expose
    private Object businessMail;
    @SerializedName("privateMail")
    @Expose
    private Object privateMail;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("comments")
    @Expose
    private String comments;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public Object getPrivatePhone() {
        return privatePhone;
    }

    public void setPrivatePhone(Object privatePhone) {
        this.privatePhone = privatePhone;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public Object getBusinessMail() {
        return businessMail;
    }

    public void setBusinessMail(Object businessMail) {
        this.businessMail = businessMail;
    }

    public Object getPrivateMail() {
        return privateMail;
    }

    public void setPrivateMail(Object privateMail) {
        this.privateMail = privateMail;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

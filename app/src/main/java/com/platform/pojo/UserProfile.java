package com.platform.pojo;

import java.util.List;

public class UserProfile {

    private Integer version;

    private Long createdAt;

    private Long updatedAt;

    private Boolean draft;

    private Integer id;

    private String name;

    private Object surname;

    private String initials;

    private String login;

    private Object currentPassword;

    private String role;

    private Object email;

    private Object phone;

    private Object language;

    private Long lastPasswordChangedDate;

    private Integer recordsPerPage;

    private List<Object> settings = null;

    private String securityPrincipalName;

    private String entityDescription;

    private String fullname;

    public UserProfile(Integer version, Long createdAt, Long updatedAt, Boolean draft, Integer id, String name, Object surname, String initials, String login, Object currentPassword, String role, Object email, Object phone, Object language, Long lastPasswordChangedDate, Integer recordsPerPage, List<Object> settings, String securityPrincipalName, String entityDescription, String fullname, String entityType, Boolean contextable) {
        this.version = version;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.draft = draft;
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.initials = initials;
        this.login = login;
        this.currentPassword = currentPassword;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.language = language;
        this.lastPasswordChangedDate = lastPasswordChangedDate;
        this.recordsPerPage = recordsPerPage;
        this.settings = settings;
        this.securityPrincipalName = securityPrincipalName;
        this.entityDescription = entityDescription;
        this.fullname = fullname;
        this.entityType = entityType;
        this.contextable = contextable;
    }

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

    public Object getSurname() {
        return surname;
    }

    public void setSurname(Object surname) {
        this.surname = surname;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Object getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(Object currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getLanguage() {
        return language;
    }

    public void setLanguage(Object language) {
        this.language = language;
    }

    public Long getLastPasswordChangedDate() {
        return lastPasswordChangedDate;
    }

    public void setLastPasswordChangedDate(Long lastPasswordChangedDate) {
        this.lastPasswordChangedDate = lastPasswordChangedDate;
    }

    public Integer getRecordsPerPage() {
        return recordsPerPage;
    }

    public void setRecordsPerPage(Integer recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

    public List<Object> getSettings() {
        return settings;
    }

    public void setSettings(List<Object> settings) {
        this.settings = settings;
    }

    public String getSecurityPrincipalName() {
        return securityPrincipalName;
    }

    public void setSecurityPrincipalName(String securityPrincipalName) {
        this.securityPrincipalName = securityPrincipalName;
    }

    public String getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    private String entityType;

    private Boolean contextable;
}

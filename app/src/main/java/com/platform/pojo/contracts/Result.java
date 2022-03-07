
package com.platform.pojo.contracts;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Result {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("endDate")
    @Expose
    private Long endDate;
    @SerializedName("withdrawDate")
    @Expose
    private Long withdrawDate;
    @SerializedName("supplierNumber")
    @Expose
    private String supplierNumber;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("recipient")
    @Expose
    private Recipient recipient;
    @SerializedName("supplier")
    @Expose
    private Supplier supplier;
    @SerializedName("overseer")
    @Expose
    private Overseer overseer;
    @SerializedName("color")
    @Expose
    private String color;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Long getWithdrawDate() {
        return withdrawDate;
    }

    public void setWithdrawDate(Long withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Overseer getOverseer() {
        return overseer;
    }

    public void setOverseer(Overseer overseer) {
        this.overseer = overseer;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}

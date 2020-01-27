package com.krupatek.courier.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "client")
public class Client {

    public Integer getClientCode() {
        return clientCode;
    }

    public void setClientCode(Integer clientCode) {
        this.clientCode = clientCode;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAddress1() {
        return clientAddress1;
    }

    public void setClientAddress1(String clientAddress1) {
        this.clientAddress1 = clientAddress1;
    }

    public String getClientAddress2() {
        return clientAddress2;
    }

    public void setClientAddress2(String clientAddress2) {
        this.clientAddress2 = clientAddress2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(Integer branch_name) {
        this.branch_name = branch_name;
    }

    public String getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(String serviceTax) {
        this.serviceTax = serviceTax;
    }

    public String getFsc() {
        return fsc;
    }

    public void setFsc(String fsc) {
        this.fsc = fsc;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getGstEnabled() {
        return gstEnabled;
    }

    public void setGstEnabled(String gstEnabled) {
        this.gstEnabled = gstEnabled;
    }

    @Id
    @Column(name = "client_code")
    private Integer clientCode;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_add1")
    private String clientAddress1;

    @Column(name = "client_add2")
    private String clientAddress2;

    @Column(name = "city")
    private String City;

    @Column(name = "phone")
    private String phone;

    @Column(name = "branch_name")
    private Integer branch_name;

    @Column(name = "s_tax")
    private String serviceTax;

    @Column(name = "fsc")
    private String fsc;

    @Column(name = "required")
    private String required;

    @Column(name = "gst_no")
    private String gstNo;

    @Column(name = "gst_enabled")
    private String gstEnabled;
}

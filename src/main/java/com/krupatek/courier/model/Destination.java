package com.krupatek.courier.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "destination")
public class Destination {
    @Column(name = "add1")
    private String address1;

    @Column(name = "add2")
    private String address2;

    @Column(name = "add3")
    private String address3;

    @Column(name = "city")
    private String city;

    @Column(name = "co_code")
    private String coCode;

    @Column(name = "cont_person")
    private String contactPerson;

    @Id
    @Column(name = "dest_code")
    private String destCode;

    @Column(name = "dest_name")
    private String destName;

    @Column(name = "dest_type")
    private String destType;

    @Column(name = "modiCode")
    private String modiCode;

    @Column(name = "modiDate")
    private String modiDate;

    @Column(name = "phone")
    private String phone;

    @Column(name = "regional_code")
    private String regionalCode;

    @Column(name = "required")
    private String required;

    @Column(name = "state_code")
    private String stateCode;

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCoCode() {
        return coCode;
    }

    public void setCoCode(String coCode) {
        this.coCode = coCode;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getDestCode() {
        return destCode;
    }

    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }

    public String getDestType() {
        return destType;
    }

    public void setDestType(String destType) {
        this.destType = destType;
    }

    public String getModiCode() {
        return modiCode;
    }

    public void setModiCode(String modiCode) {
        this.modiCode = modiCode;
    }

    public String getModiDate() {
        return modiDate;
    }

    public void setModiDate(String modiDate) {
        this.modiDate = modiDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegionalCode() {
        return regionalCode;
    }

    public void setRegionalCode(String regionalCode) {
        this.regionalCode = regionalCode;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
}


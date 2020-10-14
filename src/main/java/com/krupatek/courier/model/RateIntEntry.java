package com.krupatek.courier.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "rate_int_master")
public class RateIntEntry {
    @Id
    @Column(name = "rate_int_master_id")
    private Integer rateIntMasterId;

    @Column(name = "clientcode")
    private String clientName;

    @Column(name = "state_code")
    private String stateCode = "ZONE A";

    @Column(name = "pod_type")
    private String podType = "D";

    @Column(name = "mode")
    private String mode = "A";

    @Column(name = "from1")
    private Double from1 = 0.001;

    @Column(name = "to1")
    private Double to1 = 0.5;

    @Column(name = "rate")
    private Integer rate = 1200;

    @Column(name = "addwt")
    private Double addWt = 0.5;

    @Column(name = "addrt")
    private Integer addRt = 275;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getPodType() {
        return podType;
    }

    public void setPodType(String podType) {
        this.podType = podType;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Double getFrom1() {
        return from1;
    }

    public void setFrom1(Double from1) {
        this.from1 = from1;
    }

    public Double getTo1() {
        return to1;
    }

    public void setTo1(Double to1) {
        this.to1 = to1;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Double getAddWt() {
        return addWt;
    }

    public void setAddWt(Double addWt) {
        this.addWt = addWt;
    }

    public Integer getAddRt() {
        return addRt;
    }

    public void setAddRt(Integer addRt) {
        this.addRt = addRt;
    }

    public Integer getRateIntMasterId() {
        return rateIntMasterId;
    }

    public void setRateIntMasterId(Integer rateIntMasterId) {
        this.rateIntMasterId = rateIntMasterId;
    }
}

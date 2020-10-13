package com.krupatek.courier.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "rate_master")
public class RateEntry {
    @Id
    @Column(name = "rate_master_id")
    private Integer rateMasterId;

    @Column(name = "clientcode")
    private String clientName;

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "pod_type")
    private String podType;

    @Column(name = "mode")
    private String mode;

    @Column(name = "from1")
    private Double from1 = 0.001;

    @Column(name = "to1")
    private Double to1 = 0.5D;

    @Column(name = "rate")
    private Integer rate = 55;

    @Column(name = "addwt")
    private Double addWt = 0.5;

    @Column(name = "addrt")
    private Integer addRt = 40;

    @Column(name = "courier")
    private String courier;

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

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }
    public Integer getRateMasterId() {
        return rateMasterId;
    }

    public void setRateMasterId(Integer rateMasterId) {
        this.rateMasterId = rateMasterId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RateEntry){
            RateEntry rateEntry = (RateEntry) obj;
            return rateEntry.getClientName().equals(getClientName()) &&
                    rateEntry.getCourier().equals(getCourier()) &&
                    rateEntry.getMode().equals(getMode()) &&
                    rateEntry.getPodType().equals(getPodType()) &&
                    rateEntry.getStateCode().equals(getStateCode());
        } else return super.equals(obj);
    }
}

package com.krupatek.courier.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity(name = "account_copy")
public class AccountCopy {
    @Id
    @Column(name = "doc_no")
    private String docNo;

    @Column(name = "pod_dt", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date podDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

    @Column(name = "pod_type")
    private String podType = "Cr";

    @Column(name = "d_p")
    private String dP = "D";

    @Column(name = "mode")
    private String mode = "L";

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "party_code")
    private Integer partyCode;

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "place_code")
    private String placeCode;

    @Column(name = "destination")
    private String destination;

    @Column(name = "weight")
    private Double weight = 0.0;

    @Column(name = "rate")
    private Integer rate = 1;

    @Column(name = "bill_no")
    private String billNo;

    @Column(name = "bill_check")
    private String billCheck;

    @Column(name = "pod")
    private String pod;

    @Column(name = "required")
    private String required;

    @Column(name = "place")
    private Integer place = 0;

    @Column(name = "from1")
    private Double from1 = 0.0;

    @Column(name = "to1")
    private Double to1 = 0.0;

    @Column(name = "to_party")
    private String toParty;

    @Column(name = "gpo_code")
    private String gpoCode;

    @Column(name = "area")
    private String area;

    @Column(name = "status")
    private String status;

    @Column(name = "status_date", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());;

    @Column(name = "remark")
    private String remark;

    @Column(name = "phone")
    private String phone;

    @Column(name = "other_chrg")
    private String otherCharges;

    @Column(name = "type")
    private String type = "Dom";

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public Date getPodDate() {
        return podDate;
    }

    public void setPodDate(Date podDate) {
        this.podDate = podDate;
    }

    public String getPodType() {
        return podType;
    }

    public void setPodType(String podType) {
        this.podType = podType;
    }

    public String getdP() {
        return dP;
    }

    public void setdP(String dP) {
        this.dP = dP;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Integer getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(Integer partyCode) {
        this.partyCode = partyCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillCheck() {
        return billCheck;
    }

    public void setBillCheck(String billCheck) {
        this.billCheck = billCheck;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
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

    public String getToParty() {
        return toParty;
    }

    public void setToParty(String toParty) {
        this.toParty = toParty;
    }

    public String getGpoCode() {
        return gpoCode;
    }

    public void setGpoCode(String gpoCode) {
        this.gpoCode = gpoCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(String otherCharges) {
        this.otherCharges = otherCharges;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.krupatek.courier.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "bill_generation")
public class BillGeneration implements Cloneable {
    @Id
    @Column(name = "bill_no")
    private String billNo;

    @Column(name = "st_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "bill_year")
    private Integer billYear;

    @Column(name = "bill_amount")
    private Integer billAmount;

    @Column(name = "pay_amt")
    private String payAmt;

    @Column(name = "pay_date")
    private String payDate;

    @Column(name = "pay_mode")
    private String payMode;

    @Column(name = "type")
    private String type;

    @Column(name = "net_amt")
    private Integer netAmount;

    @Column(name = "bal")
    private Integer balance;

    @Column(name = "required")
    private String required;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "bill_status")
    private String billStatus;

    @Column(name = "bill_month")
    private Integer billMonth;

    @Column(name = "bill_date")
    private String billDate ;

    @Column(name = "cgst")
    private Float cgst;

    @Column(name = "sgst")
    private Float sgst;

    @Column(name = "igst")
    private Float igst;

    @Column(name = "fuel_surcharge")
    private Float fuelSurcharge;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getBillYear() {
        return billYear;
    }

    public void setBillYear(Integer billYear) {
        this.billYear = billYear;
    }

    public Integer getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(Integer billAmount) {
        this.billAmount = billAmount;
    }

    public String getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(String payAmt) {
        this.payAmt = payAmt;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Integer netAmount) {
        this.netAmount = netAmount;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public Integer getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(Integer billMonth) {
        this.billMonth = billMonth;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public Float getCgst() {
        return cgst;
    }

    public void setCgst(Float cgst) {
        this.cgst = cgst;
    }

    public Float getSgst() {
        return sgst;
    }

    public void setSgst(Float sgst) {
        this.sgst = sgst;
    }

    public Float getIgst() {
        return igst;
    }

    public void setIgst(Float igst) {
        this.igst = igst;
    }

    public Float getFuelSurcharge() {
        return fuelSurcharge;
    }

    public void setFuelSurcharge(Float fuelSurcharge) {
        this.fuelSurcharge = fuelSurcharge;
    }
}

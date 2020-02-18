package com.krupatek.courier.model;

public class ClientBillFilter {
    private String billNoFilter = "";
    private String invoiceDateFilter = "";

    public String getClientNameFilter() {
        return clientNameFilter;
    }

    public void setClientNameFilter(String clientNameFilter) {
        this.clientNameFilter = clientNameFilter;
    }

    private String clientNameFilter = "";

    public String getBillNoFilter() {
        return billNoFilter;
    }

    public void setBillNoFilter(String billNoFilter) {
        this.billNoFilter = billNoFilter;
    }

    public String getInvoiceDateFilter() {
        return invoiceDateFilter;
    }

    public void setInvoiceDateFilter(String invoiceDateFilter) {
        this.invoiceDateFilter = invoiceDateFilter;
    }
}

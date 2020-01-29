package com.krupatek.courier.model;

public class AccountCopyFilter {
    private String docNoFilter = "";
    private String clientNameFilter = "";
    public AccountCopyFilter(){

    }
    public String getDocNoFilter() {
        return docNoFilter;
    }
    public void setDocNoFilter(String docNoFilter) {
        this.docNoFilter = docNoFilter;
    }
    public String getClientNameFilter() {
        return clientNameFilter;
    }

    public void setClientNameFilter(String clientNameFilter) {
        this.clientNameFilter = clientNameFilter;
    }
}

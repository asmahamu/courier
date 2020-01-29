package com.krupatek.courier.model;

public class AccountCopyFilter {
    private String docNoFilter = "";
    public AccountCopyFilter(){

    }
    public AccountCopyFilter(String docNoFilter){
        this.docNoFilter = docNoFilter;
    }
    public String getDocNoFilter() {
        return docNoFilter;
    }
    public void setDocNoFilter(String docNoFilter) {
        this.docNoFilter = docNoFilter;
    }
}

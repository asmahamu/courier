package com.krupatek.courier.model;

import java.io.Serializable;

public class NetworkId implements Serializable {
    private String netName;
    private String countryName;

    public NetworkId(){}
    public NetworkId(String netName, String countryName) {
        this.netName = netName;
        this.countryName = countryName;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}

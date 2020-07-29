package com.krupatek.courier.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity(name = "i_network")
@IdClass(NetworkId.class)
public class Network {

    @Id
    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_id")
    private String countryId;

    @Column(name = "zone_name")
    private String zoneName;

    @Id
    @Column(name = "net_name")
    private String netName;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }
}

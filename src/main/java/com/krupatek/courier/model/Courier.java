package com.krupatek.courier.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "courier")
public class Courier {
    @Id
    @Column(name = "courier_id")
    private Integer courierId;

    @Column(name = "courier_name")
    private String courierName;

    public Integer getCourierId() {
        return courierId;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }
}

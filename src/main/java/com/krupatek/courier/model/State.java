package com.krupatek.courier.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "state")
public class State {

    @Id
    @Column(name= "state_id")
    private Integer stateId;

    @Column(name= "state_name")
    private  String stateName;

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}

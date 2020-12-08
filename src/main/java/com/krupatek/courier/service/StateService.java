package com.krupatek.courier.service;

import com.krupatek.courier.model.State;

import java.util.List;
import java.util.Set;

public interface StateService {
    List<State> findAll();
    Set<String> findAllOrderByStateName();


}

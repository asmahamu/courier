package com.krupatek.courier.service;

import com.krupatek.courier.model.State;
import com.krupatek.courier.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class StateServiceImpl implements StateService{

    @Autowired
    StateRepository stateRepository;

    @Override

    public List<State> findAll(){
        return stateRepository.findAll();
    }
    public Set<String> findAllOrderByStateName(){
        return  stateRepository.findAllOrderByStateName();
    }

}

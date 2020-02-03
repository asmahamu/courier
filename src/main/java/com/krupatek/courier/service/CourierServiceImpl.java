package com.krupatek.courier.service;

import com.krupatek.courier.model.Courier;
import com.krupatek.courier.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourierServiceImpl implements CourierService {

    @Autowired
    CourierRepository courierRepository;


    @Override
    public List<Courier> findAll() {
        return courierRepository.findAll();
    }
}

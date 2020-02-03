package com.krupatek.courier.service;

import com.krupatek.courier.model.Courier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourierService {

    List<Courier> findAll();

}

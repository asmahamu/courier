package com.krupatek.courier.service;

import com.krupatek.courier.model.Zones;
import com.krupatek.courier.repository.ZonesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZonesServiceImpl implements ZonesService{
    @Autowired
    ZonesRepository zonesRepository;

    @Override
    public List<Zones> findAll() {
        return zonesRepository.findAll();
    }
}

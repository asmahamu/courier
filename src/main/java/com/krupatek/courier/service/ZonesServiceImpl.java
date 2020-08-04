package com.krupatek.courier.service;

import com.krupatek.courier.model.Zones;
import com.krupatek.courier.repository.ZonesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ZonesServiceImpl implements ZonesService{
    @Autowired
    ZonesRepository zonesRepository;

    @Override
    public List<Zones> findAll() {
        return zonesRepository.findAll();
    }

    @Override
    public Zones save(Zones zone) {
        return zonesRepository.save(zone);
    }

    @Override
    public void deleteAll(Set<Zones> zones) {
        zonesRepository.deleteAll(zones);
    }
}

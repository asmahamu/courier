package com.krupatek.courier.service;

import com.krupatek.courier.model.Zones;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ZonesService {
    List<Zones> findAll();
    Zones save(Zones zone);
    void deleteAll(Set<Zones> zones);
}

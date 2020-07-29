package com.krupatek.courier.service;

import com.krupatek.courier.model.Zones;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ZonesService {
    List<Zones> findAll();
}

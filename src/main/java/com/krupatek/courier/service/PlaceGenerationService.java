package com.krupatek.courier.service;

import com.krupatek.courier.model.PlaceGeneration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface PlaceGenerationService {
    PlaceGeneration findByCityName(String cityName);
    List<PlaceGeneration> findAll();
    Set<String> findDistinctCityName();
}

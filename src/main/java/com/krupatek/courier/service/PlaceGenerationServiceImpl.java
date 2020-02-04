package com.krupatek.courier.service;

import com.krupatek.courier.model.PlaceGeneration;
import com.krupatek.courier.repository.PlaceGenerationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PlaceGenerationServiceImpl implements PlaceGenerationService {

    @Autowired
    PlaceGenerationRepository placeGenerationRepository;

    @Override
    public PlaceGeneration findByCityName(String cityName) {
        return placeGenerationRepository.findByCityName(cityName);
    }

    @Override
    public List<PlaceGeneration> findAll() {
        return placeGenerationRepository.findAll();
    }

    @Override
    public Set<String> findDistinctCityName() {
        return placeGenerationRepository.findDistinctCityName();
    }
}

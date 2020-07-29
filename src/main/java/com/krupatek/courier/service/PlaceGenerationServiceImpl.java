package com.krupatek.courier.service;

import com.krupatek.courier.model.PlaceGeneration;
import com.krupatek.courier.repository.PlaceGenerationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.SortedSet;

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
    public SortedSet<String> findDistinctCityName() {
        return placeGenerationRepository.findDistinctCityNameOrderByCityName();
    }

    @Override
    public List<PlaceGeneration> findAllByPlaceCode(String placeCode) {
        return placeGenerationRepository.findAllByPlaceCodeOrderByCityName(placeCode);
    }

    @Transactional
    @Override
    public void updateCityWithZone(String cityName, String placeCode) {
        placeGenerationRepository.updateCityWithZone(cityName, placeCode);
    }
}

package com.krupatek.courier.service;

import com.krupatek.courier.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    CountryRepository countryRepository;

    @Override
    public Set<String> findAllOrderByCountryName() {
        return countryRepository.findAllOrderByCountryName();
    }
}

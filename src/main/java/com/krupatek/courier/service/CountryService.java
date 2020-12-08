package com.krupatek.courier.service;

import com.krupatek.courier.model.Country;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface CountryService {
    Set<String> findAllOrderByCountryName();
    Optional<Country> findByCountryName(String countryName);
    List<Country> findAll();

    Country saveAndFlush(Country country);
}

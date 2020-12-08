package com.krupatek.courier.service;

import com.krupatek.courier.model.Country;
import com.krupatek.courier.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public  class CountryServiceImpl implements CountryService {
    @Autowired
    CountryRepository countryRepository;

    @Override
    public  List<Country> findAll(){
        return  countryRepository.findAll();

    }

    @Override
    public Optional<Country> findByCountryName(String countryName){
        return  countryRepository.findByCountryName(countryName);
    }




    @Override
    public Set<String> findAllOrderByCountryName() {
        return countryRepository.findAllOrderByCountryName();
    }

    @Override
    public Country saveAndFlush(Country country) {
        return countryRepository.saveAndFlush(country);
    }
}

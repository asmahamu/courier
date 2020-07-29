package com.krupatek.courier.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface CountryService {
    Set<String> findAllOrderByCountryName();
}

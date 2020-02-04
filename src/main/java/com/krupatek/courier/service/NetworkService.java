package com.krupatek.courier.service;

import com.krupatek.courier.model.Network;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface NetworkService {
    Set<String> findDistinctCountry();
    Optional<Network> findOne(String cityName);
}

package com.krupatek.courier.service;

import com.krupatek.courier.model.Network;
import com.krupatek.courier.repository.NetworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class NetworkServiceImpl implements NetworkService {

    @Autowired
    NetworkRepository networkRepository;

    @Override
    public Set<String> findDistinctCountry() {
        return networkRepository.findDistinctCountry();
    }

    @Override
    public Optional<Network> findOne(String cityName) {
        return networkRepository.findById(cityName);
    }

    @Override
    public Set<String> findDistinctZonesForNetwork(String netName) {
        return networkRepository.findDistinctZonesForNetwork(netName);
    }

    @Override
    public Set<String> findCountryByNetworkAndZone(String netName, String zoneName) {
        return networkRepository.findCountryByNetworkAndZone(netName, zoneName);
    }
}

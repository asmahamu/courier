package com.krupatek.courier.service;

import com.krupatek.courier.model.Network;
import com.krupatek.courier.model.NetworkId;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface NetworkService {
    Set<String> findDistinctCountry();
    Optional<Network> findOne(NetworkId networkId);
    Set<String> findDistinctZonesForNetwork(String netName);
    Set<String> findCountryByNetworkAndZone(String netName, String zoneName);
    Network save(Network network);
    void delete(Network network);
}

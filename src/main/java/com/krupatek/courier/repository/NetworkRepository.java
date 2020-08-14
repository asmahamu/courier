package com.krupatek.courier.repository;

import com.krupatek.courier.model.Network;
import com.krupatek.courier.model.NetworkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NetworkRepository extends JpaRepository<Network, NetworkId> {
    @Query(value = "SELECT distinct(country_name) FROM easynew.i_network ORDER BY country_name;", nativeQuery = true)
    Set<String> findDistinctCountry();

    @Query(value = "SELECT distinct(zone_name) FROM easynew.i_network WHERE net_name = :netName ORDER BY zone_name;", nativeQuery = true)
    Set<String> findDistinctZonesForNetwork(@Param("netName") String netName);

    @Query(value = "SELECT country_name FROM easynew.i_network WHERE net_name = :netName and zone_name = :zoneName ORDER BY country_name;", nativeQuery = true)
    Set<String> findCountryByNetworkAndZone(
            @Param("netName") String netName,
            @Param("zoneName") String zoneName);



}

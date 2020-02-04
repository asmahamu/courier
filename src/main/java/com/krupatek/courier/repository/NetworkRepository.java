package com.krupatek.courier.repository;

import com.krupatek.courier.model.Network;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NetworkRepository extends JpaRepository<Network, String> {
    @Query(value = "SELECT distinct(country_name) FROM easynew.i_network;", nativeQuery = true)
    Set<String> findDistinctCountry();
}

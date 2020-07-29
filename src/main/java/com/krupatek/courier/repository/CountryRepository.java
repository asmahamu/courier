package com.krupatek.courier.repository;

import com.krupatek.courier.model.Zones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CountryRepository extends JpaRepository<Zones, String> {
    @Query(value = "SELECT country_name FROM easynew.country ORDER BY country_name;", nativeQuery = true)
    Set<String> findAllOrderByCountryName();
}

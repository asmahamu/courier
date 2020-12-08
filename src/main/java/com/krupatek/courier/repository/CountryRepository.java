package com.krupatek.courier.repository;

import com.krupatek.courier.model.Country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
    @Query(value = "SELECT country_name FROM easynew.country ORDER BY country_name;", nativeQuery = true)
    Set<String> findAllOrderByCountryName();


    Optional<Country> findByCountryName(String countryName);


}

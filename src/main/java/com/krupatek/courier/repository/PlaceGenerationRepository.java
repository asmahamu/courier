package com.krupatek.courier.repository;

import com.krupatek.courier.model.PlaceGeneration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PlaceGenerationRepository extends JpaRepository<PlaceGeneration, Integer> {
    PlaceGeneration findByCityName(String cityName);
    @Query(value = "SELECT distinct(city_name) FROM easynew.place_generation ORDER BY city_name;", nativeQuery = true)
    Set<String> findDistinctCityName();
}

package com.krupatek.courier.repository;

import com.krupatek.courier.model.RateIntEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RateIntMasterRepository extends JpaRepository<RateIntEntry, Integer> {
    List<RateIntEntry> findAllByClientName(String clientName);
    RateIntEntry findByClientNameAndStateCodeAndPodTypeAndMode(String clientName, String stateCode, String podType, String mode);
    @Query(value = "SELECT distinct(clientcode) FROM easynew.rate_int_master", nativeQuery = true)
    Set<String> findDistinctClientName();
}

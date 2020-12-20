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

    @Query(value = "SELECT distinct(clientcode) FROM easynew.rate_int_master ORDER BY clientcode", nativeQuery = true)
    Set<String> findDistinctClientName();

    @Query(value = "SELECT distinct(clientcode) FROM easynew.rate_int_master as rim,  easynew.client as cl where rim.clientcode = cl.client_name and cl.enabled = 'Yes' ORDER BY clientcode;", nativeQuery = true)
    Set<String> findEnabledDistinctClientName();


    @Query(value = "Select max(rate_int_master_id) from easynew.rate_int_master", nativeQuery = true)
    Integer latestIntMasterId();
}

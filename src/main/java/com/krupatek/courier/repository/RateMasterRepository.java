package com.krupatek.courier.repository;

import com.krupatek.courier.model.RateEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RateMasterRepository extends JpaRepository<RateEntry, Integer> {
    List<RateEntry> findAllByClientNameAndCourier(String clientName, String courier);
    RateEntry findByClientNameAndCourierAndStateCodeAndPodTypeAndMode(String clientName, String courier, String stateCode, String podType, String mode);

    @Query(value = "SELECT distinct(clientcode) FROM easynew.rate_master ORDER BY clientcode", nativeQuery = true)
    Set<String> findDistinctClientName();

    @Query(value = "SELECT distinct(clientcode) FROM easynew.rate_master as rm, easynew.client as cl where rm.clientcode = cl.client_name and cl.enabled = 'Yes' ORDER BY clientcode", nativeQuery = true)
    Set<String> findEnabledDistinctClientName();


    @Query(value = "Select max(rate_master_id) from easynew.rate_master", nativeQuery = true)
    Integer latestMasterId();
}

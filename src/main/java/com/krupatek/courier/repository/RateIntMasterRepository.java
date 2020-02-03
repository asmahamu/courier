package com.krupatek.courier.repository;

import com.krupatek.courier.model.RateIntEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateIntMasterRepository extends JpaRepository<RateIntEntry, Integer> {
    List<RateIntEntry> findAllByClientName(String clientName);
}

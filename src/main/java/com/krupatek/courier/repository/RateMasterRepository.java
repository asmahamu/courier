package com.krupatek.courier.repository;

import com.krupatek.courier.model.RateEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateMasterRepository extends JpaRepository<RateEntry, Integer> {
    List<RateEntry> findAllByClientNameAndCourier(String clientName, String courier);
}

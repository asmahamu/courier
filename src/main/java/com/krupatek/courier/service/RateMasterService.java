package com.krupatek.courier.service;

import com.krupatek.courier.model.RateEntry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface RateMasterService {
    List<RateEntry> findAllByClientNameAndCourier(String clientName, String courier);

    RateEntry saveAndFlush(RateEntry rateEntry);

    RateEntry findByClientNameAndCourierAndStateCodeAndPodTypeAndMode(String clientName, String courier, String stateCode, String podType, String mode);

    Set<String> findDistinctClientName();

    Set<String> findEnabledDistinctClientName();

    Integer latestMasterId();

    void delete(RateEntry rateEntry);
}

package com.krupatek.courier.service;

import com.krupatek.courier.model.RateIntEntry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface RateIntMasterService {
    List<RateIntEntry> findAllByClientName(String clientName);
    RateIntEntry saveAndFlush(RateIntEntry rateEntry);
    RateIntEntry findByClientNameAndStateCodeAndPodTypeAndMode(String clientName, String stateCode, String podType, String mode);
    Set<String> findDistinctClientName();
    Integer latestIntMasterId();

    void delete(RateIntEntry rateIntEntry);
}

package com.krupatek.courier.service;

import com.krupatek.courier.model.RateIntEntry;
import com.krupatek.courier.repository.RateIntMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RateIntMasterServiceImpl implements RateIntMasterService {
    @Autowired
    RateIntMasterRepository rateIntMasterRepository;

    @Override
    public List<RateIntEntry> findAllByClientName(String clientName) {
        return rateIntMasterRepository.findAllByClientName(clientName);
    }

    @Override
    public RateIntEntry saveAndFlush(RateIntEntry rateEntry) {
        return rateIntMasterRepository.saveAndFlush(rateEntry);
    }

    @Override
    public RateIntEntry findByClientNameAndStateCodeAndPodTypeAndMode(String clientName, String stateCode, String podType, String mode) {
        return rateIntMasterRepository.findByClientNameAndStateCodeAndPodTypeAndMode(clientName, stateCode, podType, mode);
    }

    @Override
    public Set<String> findDistinctClientName() {
        return rateIntMasterRepository.findDistinctClientName();
    }

    @Override
    public Set<String> findEnabledDistinctClientName() {
        return rateIntMasterRepository.findEnabledDistinctClientName();
    }

    @Override
    public Integer latestIntMasterId() {
        return rateIntMasterRepository.latestIntMasterId();
    }

    @Override
    public void delete(RateIntEntry rateIntEntry) {
        rateIntMasterRepository.delete(rateIntEntry);
    }
}

package com.krupatek.courier.service;

import com.krupatek.courier.model.RateEntry;
import com.krupatek.courier.repository.RateMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RateMasterServiceImpl implements RateMasterService {
    @Autowired
    RateMasterRepository rateMasterRepository;

    @Override
    public List<RateEntry> findAllByClientNameAndCourier(String clientName, String courier) {
        return rateMasterRepository.findAllByClientNameAndCourier(clientName, courier);
    }

    @Override
    public RateEntry saveAndFlush(RateEntry rateEntry) {
        return rateMasterRepository.saveAndFlush(rateEntry);
    }

    @Override
    public RateEntry findByClientNameAndCourierAndStateCodeAndPodTypeAndMode(String clientName, String courier, String stateCode, String podType, String mode) {
        return rateMasterRepository.findByClientNameAndCourierAndStateCodeAndPodTypeAndMode(clientName, courier, stateCode, podType, mode);
    }

    @Override
    public Set<String> findDistinctClientName() {
        return rateMasterRepository.findDistinctClientName();
    }

    @Override
    public Integer latestMasterId() {
        return rateMasterRepository.latestMasterId();
    }

    @Override
    public void delete(RateEntry rateEntry) {
        rateMasterRepository.delete(rateEntry);
    }
}

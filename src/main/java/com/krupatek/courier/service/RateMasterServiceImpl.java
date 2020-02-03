package com.krupatek.courier.service;

import com.krupatek.courier.model.RateEntry;
import com.krupatek.courier.repository.RateMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

package com.krupatek.courier.service;

import com.krupatek.courier.model.RateIntEntry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RateIntMasterService {
    List<RateIntEntry> findAllByClientName(String clientName);
    RateIntEntry saveAndFlush(RateIntEntry rateEntry);
}

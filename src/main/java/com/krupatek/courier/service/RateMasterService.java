package com.krupatek.courier.service;

import com.krupatek.courier.model.RateEntry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RateMasterService {
    List<RateEntry> findAllByClientNameAndCourier(String clientName, String courier);
    RateEntry saveAndFlush(RateEntry rateEntry);
}

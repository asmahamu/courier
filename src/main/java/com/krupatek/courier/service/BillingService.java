package com.krupatek.courier.service;

import com.krupatek.courier.model.BillGeneration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BillingService {
    List<BillGeneration> findAll();
    Optional<BillGeneration> findOne(String billNo);
}

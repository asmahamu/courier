package com.krupatek.courier.service;

import com.krupatek.courier.model.BillGeneration;
import com.krupatek.courier.repository.BillGenerationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    BillGenerationRepository billGenerationRepository;

    @Override
    public List<BillGeneration> findAll() {
        return billGenerationRepository.findAll();
    }

    @Override
    public Optional<BillGeneration> findOne(String billNo) {
        return billGenerationRepository.findById(billNo);
    }
}

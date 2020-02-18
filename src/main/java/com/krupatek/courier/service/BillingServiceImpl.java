package com.krupatek.courier.service;

import com.krupatek.courier.model.BillGeneration;
import com.krupatek.courier.repository.BillGenerationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<BillGeneration> findByBillNoStartsWithAndBillDateStartsWithAndClientNameStartsWith(int offset, int limit, String billNoFilter, String invoiceDateFilter, String clientNameFilter) {
        Pageable pageable = PageRequest.of(offset, limit);
        return billGenerationRepository.findByBillNoStartsWithAndBillDateStartsWithAndClientNameStartsWith(billNoFilter, invoiceDateFilter, clientNameFilter, pageable);
    }

    @Override
    public long countByBillNoStartsWithAndBillDateStartsWithAndClientNameStartsWith(String billNoFilter, String invoiceDateFilter, String clientNameFilter) {
        return billGenerationRepository.countByBillNoStartsWithAndBillDateStartsWithAndClientNameStartsWith(billNoFilter, invoiceDateFilter, clientNameFilter);
    }
}

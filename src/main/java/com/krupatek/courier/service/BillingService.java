package com.krupatek.courier.service;

import com.krupatek.courier.model.BillGeneration;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BillingService {
    List<BillGeneration> findAll();
    Optional<BillGeneration> findOne(String billNo);

    Page<BillGeneration> findByBillNoStartsWithAndBillDateStartsWithAndClientNameStartsWith(int offset, int limit, String billNoFilter, String invoiceDateFilter, String clientNameFilter);

    long countByBillNoStartsWithAndBillDateStartsWithAndClientNameStartsWith(String billNoFilter, String invoiceDateFilter, String clientNameFilter);

    String lastBillNo(String billNoLike);

    String nextBillNo();

    BillGeneration saveAndFlush(BillGeneration billGeneration);
}

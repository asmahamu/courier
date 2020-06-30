package com.krupatek.courier.service;

import com.krupatek.courier.model.BillGeneration;
import com.krupatek.courier.repository.BillGenerationRepository;
import com.krupatek.courier.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    BillGenerationRepository billGenerationRepository;

    @Autowired
    DateUtils dateUtils;

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
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "billDate"));
        return billGenerationRepository.findByAndBillNoStartsWithAndBillDateStartsWithAndClientNameStartsWith(billNoFilter, invoiceDateFilter, clientNameFilter, pageable);
    }

    @Override
    public long countByBillNoStartsWithAndBillDateStartsWithAndClientNameStartsWith(String billNoFilter, String invoiceDateFilter, String clientNameFilter) {
        return billGenerationRepository.countByBillNoStartsWithAndBillDateStartsWithAndClientNameStartsWith(billNoFilter, invoiceDateFilter, clientNameFilter);
    }

    @Override
    public String lastBillNo(String billNoLike) {
        return billGenerationRepository.lastBillNo(billNoLike);
    }

    @Override
    public String nextBillNo() {
        String billTag = "GST/" + dateUtils.currentFiscalYear(LocalDate.now()) + "/";
        String lastBillNo = lastBillNo(billTag + "%");
        if(lastBillNo == null || lastBillNo.isEmpty()) {
            lastBillNo = billTag+1;
        } else {
            lastBillNo = billTag + (Integer.parseInt(lastBillNo.split("/")[2]) + 1);
        }
        return lastBillNo;
    }

    @Override
    public BillGeneration saveAndFlush(BillGeneration billGeneration) {
        return billGenerationRepository.saveAndFlush(billGeneration);
    }
}

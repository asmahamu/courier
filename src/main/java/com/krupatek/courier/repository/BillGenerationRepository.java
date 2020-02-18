package com.krupatek.courier.repository;

import com.krupatek.courier.model.BillGeneration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillGenerationRepository extends JpaRepository<BillGeneration, String> {
    Page<BillGeneration> findByBillNoStartsWithAndBillDateStartsWithAndClientNameStartsWith(
            String billNoFilter,
            String invoiceDateFilter,
            String clientNameFilter,
            Pageable pageable);

    long countByBillNoStartsWithAndBillDateStartsWithAndClientNameStartsWith(String billNoFilter, String invoiceDateFilter, String clientNameFilter);
}


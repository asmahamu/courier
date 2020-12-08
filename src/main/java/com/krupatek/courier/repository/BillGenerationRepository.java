package com.krupatek.courier.repository;

import com.krupatek.courier.model.BillGeneration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;

@Repository
public interface BillGenerationRepository extends JpaRepository<BillGeneration, String> {

    Page<BillGeneration> findByAndBillNoStartsWithAndBillDateContainingAndClientNameStartsWith(
            String billNoFilter,
            String invoiceDateFilter,
            String clientNameFilter,
            Pageable pageable);

    long countByBillNoStartsWithAndBillDateContainingAndClientNameStartsWith(
            String billNoFilter,
            String invoiceDateFilter,
            String clientNameFilter);

    @Query(value = "SELECT max(CAST(SUBSTRING(bill_no from 11) AS UNSIGNED))  FROM easynew.bill_generation where bill_no like :billNoLike", nativeQuery = true)
    String lastBillNo(@Param("billNoLike") String billNoLike);
}


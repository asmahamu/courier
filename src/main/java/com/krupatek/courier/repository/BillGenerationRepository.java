package com.krupatek.courier.repository;

import com.krupatek.courier.model.BillGeneration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillGenerationRepository extends JpaRepository<BillGeneration, String> {
    Page<BillGeneration> findByBillNoStartsWithAndBillDateStartsWithAndClientNameStartsWith(
            String billNoFilter,
            String invoiceDateFilter,
            String clientNameFilter,
            Pageable pageable);

    long countByBillNoStartsWithAndBillDateStartsWithAndClientNameStartsWith(
            String billNoFilter,
            String invoiceDateFilter,
            String clientNameFilter);

    @Query(value = "SELECT bill_no FROM easynew.bill_generation where bill_no like :billNoLike order by bill_no desc limit 1;", nativeQuery = true)
    String lastBillNo(@Param("billNoLike") String billNoLike);
}


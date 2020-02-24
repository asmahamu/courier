package com.krupatek.courier.repository;

import com.krupatek.courier.model.AccountCopy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccountCopyRepository extends JpaRepository<AccountCopy, String> {
    List<AccountCopy> findAllByClientName(String clientName);
    List<AccountCopy> findAllByClientNameAndPodDateBetween(String clientName, Date startDate, Date endDate);
    List<AccountCopy> findAllByPodDateBetween(Date startDate, Date endDate);
    List<AccountCopy> findAllByClientNameAndPodDateBetweenAndType(String clientName, Date startDate, Date endDate, String type);
    List<AccountCopy> findByDocNoStartsWith(String docNo);
    Page<AccountCopy> findByDocNoStartsWith(String docNo, Pageable page);
    Page<AccountCopy> findByDocNoStartsWithAndClientNameStartsWith(String docNo, String clientName, Pageable page);
    long countByDocNoStartsWith(String docNo);
    long countByDocNoStartsWithAndClientNameStartsWith(String docNo, String clientName);
    List<AccountCopy> findAllByBillNo(String billNo);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE account_copy SET bill_no =:billNo WHERE client_name = :clientName and type = :type and pod_dt BETWEEN :from AND :to", nativeQuery = true)
    void tagBillNo(
            @Param("clientName")
            String clientName,
            @Param("from")
            Date from,
            @Param("to")
            Date to,
            @Param("type")
            String type,
            @Param("billNo")
            String billNo);

    AccountCopy findOneByDocNo(String docNo);
}

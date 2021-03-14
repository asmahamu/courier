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
import java.util.Optional;

@Repository
public interface AccountCopyRepository extends JpaRepository<AccountCopy, String> {
    List<AccountCopy> findAllByClientName(String clientName);
    List<AccountCopy> findAllByClientNameAndPodDateBetweenOrderByPodDate(String clientName, Date startDate, Date endDate);
    List<AccountCopy> findAllByPodDateBetweenOrderByClientName(Date startDate, Date endDate);
    List<AccountCopy> findAllByClientNameAndPodDateBetweenAndTypeOrderByPodDate(String clientName, Date startDate, Date endDate, String type);
    List<AccountCopy> findByDocNoStartsWith(String docNo);
    Page<AccountCopy> findByDocNoStartsWith(String docNo, Pageable page);
    Page<AccountCopy> findByDocNoStartsWithAndClientNameStartsWithOrderByPodDateDesc(String docNo, String clientName, Pageable page);
    Page<AccountCopy> findByDocNoStartsWithAndClientNameStartsWithAndPodDateOrderByPodDateDesc(String docNo, String clientName, Date podDate, Pageable page);
    long countByDocNoStartsWith(String docNo);
    long countByDocNoStartsWithAndClientNameStartsWith(String docNo, String clientName);
    long countByDocNoStartsWithAndClientNameStartsWithAndPodDate(String docNo, String clientName, Date podDate);
    List<AccountCopy> findAllByBillNoOrderByPodDate(String billNo);

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

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE account_copy SET bill_no =\"\" WHERE bill_no = :billNo", nativeQuery = true)
    void resetBillNo(@Param("billNo") String billNo);

    Optional<AccountCopy> findOneByDocNo(String docNo);

    @Query(value = "SELECT sum(rate) FROM easynew.account_copy where doc_no LIKE :docNo% and client_name LIKE :clientName% and pod_dt LIKE :podDate%", nativeQuery = true)
    long totalByDocNoStartsWithAndClientNameStartsWithAndPodDate(String docNo, String clientName, Date podDate);

    @Query(value = "SELECT sum(rate) FROM easynew.account_copy where doc_no LIKE :docNo% and client_name LIKE :clientName%", nativeQuery = true)
    long totalByDocNoStartsWithAndClientNameStartsWith(String docNo, String clientName);
}

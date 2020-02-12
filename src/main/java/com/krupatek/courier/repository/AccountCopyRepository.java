package com.krupatek.courier.repository;

import com.krupatek.courier.model.AccountCopy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccountCopyRepository extends JpaRepository<AccountCopy, String> {
    List<AccountCopy> findAllByClientName(String clientName);
    List<AccountCopy> findAllByClientNameAndPodDateBetween(String clientName, Date startDate, Date endDate);
    List<AccountCopy> findByDocNoStartsWith(String docNo);
    Page<AccountCopy> findByDocNoStartsWith(String docNo, Pageable page);
    Page<AccountCopy> findByDocNoStartsWithAndClientNameStartsWith(String docNo, String clientName, Pageable page);
    long countByDocNoStartsWith(String docNo);
    long countByDocNoStartsWithAndClientNameStartsWith(String docNo, String clientName);
    List<AccountCopy> findAllByBillNo(String billNo);
}

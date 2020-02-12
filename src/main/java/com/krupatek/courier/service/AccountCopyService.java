package com.krupatek.courier.service;

import com.krupatek.courier.model.AccountCopy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface AccountCopyService {
    List<AccountCopy> findAllByClientNameAndPodDateBetween(String clientName, Date startDate, Date endDate);

    AccountCopy saveAndFlush(AccountCopy accountCopy);

    List<AccountCopy> findAll();

    List<AccountCopy> findByDocNoStartsWith(String docNo);

    Page<AccountCopy> findByDocNoStartsWith(int offset, int limit,
                                            String docNo);

    Page<AccountCopy> findByDocNoStartsWithAndClientNameStartsWith(int offset, int limit, String docNo, String clientName);

    long countByDocNoStartsWith(String docNo);

    long countByDocNoStartsWithAndClientNameStartsWith(String docNo, String clientName);

    List<AccountCopy> findAllByBillNo(String billNo);
}

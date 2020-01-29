package com.krupatek.courier.service;

import com.krupatek.courier.model.AccountCopy;
import com.krupatek.courier.repository.AccountCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountCopyServiceImpl implements AccountCopyService {

    @Autowired
    AccountCopyRepository accountCopyRepository;

    @Override
    public List<AccountCopy> findAllByClientNameAndPodDateBetween(String clientName, Date startDate, Date endDate) {
        return accountCopyRepository.findAllByClientNameAndPodDateBetween(clientName, startDate, endDate);
    }

    @Override
    public AccountCopy saveAndFlush(AccountCopy accountCopy) {
        return accountCopyRepository.saveAndFlush(accountCopy);
    }

    @Override
    public List<AccountCopy> findAll() {
        return accountCopyRepository.findAll();
    }

    @Override
    public List<AccountCopy> findByDocNoStartsWith(String docNo) {
        return accountCopyRepository.findByDocNoStartsWith(docNo);
    }

    @Override
    public Page<AccountCopy> findByDocNoStartsWith(int offset, int limit, String docNo) {
        Pageable pageable = PageRequest.of(offset, limit);
        return accountCopyRepository.findByDocNoStartsWith(docNo, pageable);
    }

    @Override
    public Page<AccountCopy> findByDocNoStartsWithAndClientNameStartsWith(int offset, int limit, String docNo, String clientName) {
        Pageable pageable = PageRequest.of(offset, limit);
        return accountCopyRepository.findByDocNoStartsWithAndClientNameStartsWith(docNo, clientName, pageable);
    }

    @Override
    public long countByDocNoStartsWith(String docNo) {
        return accountCopyRepository.countByDocNoStartsWith(docNo);
    }

    @Override
    public long countByDocNoStartsWithAndClientNameStartsWith(String docNo, String clientName) {
        return accountCopyRepository.countByDocNoStartsWithAndClientNameStartsWith(docNo, clientName);
    }
}

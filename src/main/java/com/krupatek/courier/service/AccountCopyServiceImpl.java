package com.krupatek.courier.service;

import com.krupatek.courier.model.AccountCopy;
import com.krupatek.courier.repository.AccountCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountCopyServiceImpl implements AccountCopyService {

    @Autowired
    AccountCopyRepository accountCopyRepository;

    @Override
    public List<AccountCopy> findAllByClientNameAndPodDateBetween(String clientName, Date startDate, Date endDate) {

        return accountCopyRepository.findAllByClientNameAndPodDateBetweenOrderByPodDateAsc(clientName, startDate, endDate);


    }

    @Override
    public List<AccountCopy> findAllByPodDateBetween(Date startDate, Date endDate) {
        return accountCopyRepository.findAllByPodDateBetweenOrderByClientName(startDate, endDate);
    }

    @Override
    public List<AccountCopy> findAllByClientNameAndPodDateBetweenAndType(String clientName, Date startDate, Date endDate, String type) {
        return accountCopyRepository.findAllByClientNameAndPodDateBetweenAndTypeOrderByPodDate(clientName, startDate, endDate, type);
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
    public Page<AccountCopy> findByDocNoStartsWithAndClientNameStartsWithAndPodDate(int offset, int limit, String docNo, String clientName, Date podDate) {
        Pageable pageable = PageRequest.of(offset, limit);
        return accountCopyRepository.findByDocNoStartsWithAndClientNameStartsWithAndPodDateOrderByPodDateDesc(docNo, clientName, podDate, pageable);
    }

    @Override
    public Page<AccountCopy> findByDocNoStartsWithAndClientNameStartsWith(int offset, int limit, String docNo, String clientName) {
        Pageable pageable = PageRequest.of(offset, limit);
        return accountCopyRepository.findByDocNoStartsWithAndClientNameStartsWithOrderByPodDateDesc(docNo, clientName, pageable);
    }

    @Override
    public long countByDocNoStartsWith(String docNo) {
        return accountCopyRepository.countByDocNoStartsWith(docNo);
    }

    @Override
    public long countByDocNoStartsWithAndClientNameStartsWith(String docNo, String clientName) {
        return accountCopyRepository.countByDocNoStartsWithAndClientNameStartsWith(docNo, clientName);
    }

    @Override
    public long countByDocNoStartsWithAndClientNameStartsWithAndPodDate(String docNo, String clientName, Date podDate) {
        return accountCopyRepository.countByDocNoStartsWithAndClientNameStartsWithAndPodDate(docNo, clientName, podDate);
    }

    @Override
    public long totalByDocNoStartsWithAndClientNameStartsWith(String docNo, String clientName) {
        return accountCopyRepository.totalByDocNoStartsWithAndClientNameStartsWith(docNo, clientName);
    }

    @Override
    public long totalByDocNoStartsWithAndClientNameStartsWithAndPodDate(String docNo, String clientName, Date podDate) {
        return accountCopyRepository.totalByDocNoStartsWithAndClientNameStartsWithAndPodDate(docNo, clientName, podDate);
    }

    @Override
    public List<AccountCopy> findAllByBillNo(String billNo) {
        return accountCopyRepository.findAllByBillNo(billNo);
    }

    @Transactional
    @Override
    public void tagBillNo(String currentSelectedItem, Date fromLocaleDate, Date fromLocaleDate1, String type, String billNo) {
        accountCopyRepository.tagBillNo(currentSelectedItem, fromLocaleDate, fromLocaleDate1, type, billNo);
    }

    @Transactional
    @Override
    public void resetBillNo(String billNo) {
        accountCopyRepository.resetBillNo(billNo);
    }

    @Override
    public Optional<AccountCopy> findOneByDocNo(String docNo) {
        return accountCopyRepository.findOneByDocNo(docNo);
    }

    @Override
    public void delete(AccountCopy accountCopy) {
        accountCopyRepository.delete(accountCopy);
    }

    @Override
    public void saveAll(List<AccountCopy> accountCopyList) {
        accountCopyRepository.saveAll(accountCopyList);
    }
}

package com.krupatek.courier.service;

import com.krupatek.courier.model.AccountCopy;
import com.krupatek.courier.repository.AccountCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}

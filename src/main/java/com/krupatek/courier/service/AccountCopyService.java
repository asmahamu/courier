package com.krupatek.courier.service;

import com.krupatek.courier.model.AccountCopy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface AccountCopyService {
    List<AccountCopy> findAllByClientNameAndPodDateBetween(String clientName, Date startDate, Date endDate);

    AccountCopy saveAndFlush(AccountCopy accountCopy);
}

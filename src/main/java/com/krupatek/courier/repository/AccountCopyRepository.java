package com.krupatek.courier.repository;

import com.krupatek.courier.model.AccountCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccountCopyRepository extends JpaRepository<AccountCopy, String> {
    List<AccountCopy> findAllByClientName(String clientName);
    List<AccountCopy> findAllByClientNameAndPodDateBetween(String clientName, Date startDate, Date endDate);
}

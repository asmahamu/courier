package com.krupatek.courier.repository;

import com.krupatek.courier.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findByOrderByClientName();
    List<Client> findAllByClientName(String clientName);
    List<Client> findByClientNameStartsWithOrderByClientName(String clientName);
    Page<Client> findByClientNameStartsWithOrderByClientName(String clientName, Pageable pageable);
    long countByClientNameStartsWith(String clientName);

    @Query(value = "SELECT client_name FROM easynew.client where enabled = 'Yes' ORDER BY client_name;", nativeQuery = true)
    Set<String> findAllEnabledOrderByClientName();

    @Query(value = "SELECT max(client_code) FROM easynew.client", nativeQuery = true)
    long findMaxClientId();
}

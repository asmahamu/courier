package com.krupatek.courier.service;

import com.krupatek.courier.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ClientService {
    List<Client> findAllByClientName(String clientName);
    List<Client> findAll();
    Set<String> findAllEnabled();
    Client saveAndFlush(Client client);
    List<Client> findByClientNameStartsWithOrderByClientName(String clientName);
    Page<Client> findByClientNameStartsWithOrderByClientName(int offset, int limit, String clientName);
    long countByClientNameStartsWith(String clientName);
    long nextClientCode();
    void delete(Client client);
}

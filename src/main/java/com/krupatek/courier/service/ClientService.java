package com.krupatek.courier.service;

import com.krupatek.courier.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {
    List<Client> findAllByClientName(String clientName);
    List<Client> findAll();
    Client saveAndFlush(Client client);
    List<Client> findByClientNameStartsWith(String clientName);
    Page<Client> findByClientNameStartsWith(int offset, int limit, String clientName);
    long countByClientNameStartsWith(String clientName);
}

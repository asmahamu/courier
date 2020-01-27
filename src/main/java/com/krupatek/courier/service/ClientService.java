package com.krupatek.courier.service;

import com.krupatek.courier.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {
    List<Client> findAllByClientName(String clientName);
    List<Client> findAll();
    Client saveAndFlush(Client client);
    List<Client> findByClientNameStartsWith(String clientName);
    List<Client> fetch(int offset, int limit,
                           String filterText);
    int getCount(String filterText);
}

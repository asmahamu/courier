package com.krupatek.courier.service;

import com.krupatek.courier.model.Client;
import com.krupatek.courier.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<Client> findAllByClientName(String clientName) {
        return clientRepository.findAllByClientName(clientName);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findByOrderByClientName();
    }

    @Override
    public Client saveAndFlush(Client client) {
        return clientRepository.saveAndFlush(client);
    }

    @Override
    public List<Client> findByClientNameStartsWith(String clientName) {
        return clientRepository.findByClientNameStartsWith(clientName);
    }

    @Override
    public Page<Client> findByClientNameStartsWith(int offset, int limit, String clientName) {
        Pageable pageable = PageRequest.of(offset, limit);
        return clientRepository.findByClientNameStartsWith(clientName, pageable);
    }

    @Override
    public long countByClientNameStartsWith(String clientName) {
        return clientRepository.countByClientNameStartsWith(clientName);
    }
}

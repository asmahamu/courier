package com.krupatek.courier.service;

import com.krupatek.courier.model.Destination;
import com.krupatek.courier.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationServiceImpl implements DestinationService {

    @Autowired
    DestinationRepository destinationRepository;

    @Override
    public List<Destination> findAll() {
        return destinationRepository.findAll();
    }
}

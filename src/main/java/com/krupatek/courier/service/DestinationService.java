package com.krupatek.courier.service;

import com.krupatek.courier.model.Destination;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DestinationService {
    List<Destination> findAll();
}

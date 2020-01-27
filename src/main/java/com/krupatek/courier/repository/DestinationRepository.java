package com.krupatek.courier.repository;

import com.krupatek.courier.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, String> {
}

package com.krupatek.courier.repository;

import com.krupatek.courier.model.BillGeneration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillGenerationRepository extends JpaRepository<BillGeneration, String> {
}


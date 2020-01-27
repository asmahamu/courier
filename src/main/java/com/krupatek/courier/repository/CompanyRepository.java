package com.krupatek.courier.repository;

import com.krupatek.courier.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
}

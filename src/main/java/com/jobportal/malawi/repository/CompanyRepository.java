package com.jobportal.malawi.repository;

import com.jobportal.malawi.models.Company;
import com.jobportal.malawi.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByNameContainingIgnoreCaseAndIndustryContainingIgnoreCase(String name, String industry);
}

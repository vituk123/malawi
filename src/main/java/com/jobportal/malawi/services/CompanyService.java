package com.jobportal.malawi.services;

import com.jobportal.malawi.payload.request.dto.CompanyRequestDTO;
import com.jobportal.malawi.payload.response.dto.CompanyResponseDTO;

import java.util.List;

public interface CompanyService {

    CompanyResponseDTO registerCompany(CompanyRequestDTO companyRequestDTO);

    CompanyResponseDTO updateCompany(Long companyId, CompanyRequestDTO companyRequestDTO);

    CompanyResponseDTO getCompanyById(Long companyId);

    List<CompanyResponseDTO> searchCompanies(String name, String industry);

    void verifyCompany(Long companyId);
}

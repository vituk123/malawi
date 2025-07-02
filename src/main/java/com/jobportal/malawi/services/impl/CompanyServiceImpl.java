package com.jobportal.malawi.services.impl;

import com.jobportal.malawi.exception.ResourceNotFoundException;
import com.jobportal.malawi.models.Company;
import com.jobportal.malawi.models.User;
import com.jobportal.malawi.payload.request.dto.CompanyRequestDTO;
import com.jobportal.malawi.payload.response.dto.CompanyResponseDTO;
import com.jobportal.malawi.repository.CompanyRepository;
import com.jobportal.malawi.repository.UserRepository;
import com.jobportal.malawi.services.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CompanyResponseDTO registerCompany(CompanyRequestDTO companyRequestDTO) {
        User user = userRepository.findById(companyRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Company company = modelMapper.map(companyRequestDTO, Company.class);
        company.setUser(user);
        company = companyRepository.save(company);
        return modelMapper.map(company, CompanyResponseDTO.class);
    }

    @Override
    public CompanyResponseDTO updateCompany(Long companyId, CompanyRequestDTO companyRequestDTO) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        modelMapper.map(companyRequestDTO, company);
        company = companyRepository.save(company);
        return modelMapper.map(company, CompanyResponseDTO.class);
    }

    @Override
    public CompanyResponseDTO getCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        return modelMapper.map(company, CompanyResponseDTO.class);
    }

    @Override
    public List<CompanyResponseDTO> searchCompanies(String name, String industry) {
        return companyRepository.findByNameContainingIgnoreCaseAndIndustryContainingIgnoreCase(name, industry).stream()
                .map(company -> modelMapper.map(company, CompanyResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void verifyCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        // Add verification logic here
        companyRepository.save(company);
    }
}

package com.jobportal.malawi.controllers;

import com.jobportal.malawi.payload.request.dto.CompanyRequestDTO;
import com.jobportal.malawi.payload.response.dto.CompanyResponseDTO;
import com.jobportal.malawi.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
    public ResponseEntity<CompanyResponseDTO> registerCompany(@RequestBody CompanyRequestDTO companyRequestDTO) {
        return new ResponseEntity<>(companyService.registerCompany(companyRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{companyId}")
    @PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
    public ResponseEntity<CompanyResponseDTO> updateCompany(@PathVariable Long companyId, @RequestBody CompanyRequestDTO companyRequestDTO) {
        return ResponseEntity.ok(companyService.updateCompany(companyId, companyRequestDTO));
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponseDTO> getCompanyById(@PathVariable Long companyId) {
        return ResponseEntity.ok(companyService.getCompanyById(companyId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CompanyResponseDTO>> searchCompanies(@RequestParam(required = false) String name, @RequestParam(required = false) String industry) {
        return ResponseEntity.ok(companyService.searchCompanies(name, industry));
    }

    @PutMapping("/{companyId}/verify")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> verifyCompany(@PathVariable Long companyId) {
        companyService.verifyCompany(companyId);
        return ResponseEntity.noContent().build();
    }
}

package com.jobportal.malawi.config;

import com.jobportal.malawi.models.ERole;
import com.jobportal.malawi.models.Role;
import com.jobportal.malawi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName(ERole.ROLE_JOB_SEEKER).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_JOB_SEEKER));
        }
        if (roleRepository.findByName(ERole.ROLE_EMPLOYER).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_EMPLOYER));
        }
        if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }
    }
}

package com.jobportal.malawi.repository;

import com.jobportal.malawi.models.JobApplication;
import com.jobportal.malawi.models.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    Optional<JobApplication> findByUserIdAndJobId(Long userId, Long jobId);

    List<JobApplication> findByJobId(Long jobId);

    List<JobApplication> findByUserId(Long userId);
}

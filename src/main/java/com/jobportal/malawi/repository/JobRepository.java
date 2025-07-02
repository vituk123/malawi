package com.jobportal.malawi.repository;

import com.jobportal.malawi.models.Job;
import com.jobportal.malawi.models.Job;
import com.jobportal.malawi.models.JobStatus;
import com.jobportal.malawi.models.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCaseAndCompany_NameContainingIgnoreCase(String title, String location, String companyName, Pageable pageable);

    Page<Job> findBySalaryBetweenAndJobTypeAndLocationContainingIgnoreCase(double minSalary, double maxSalary, JobType jobType, String location, Pageable pageable);

    List<Job> findByCompanyId(Long companyId);

    @Query("UPDATE Job j SET j.status = :status WHERE j.id = :id")
    void softDelete(@Param("id") Long id, @Param("status") JobStatus status);
}

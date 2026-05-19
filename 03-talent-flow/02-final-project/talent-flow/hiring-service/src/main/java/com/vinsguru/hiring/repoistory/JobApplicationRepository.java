package com.vinsguru.hiring.repoistory;

import com.vinsguru.hiring.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {

    List<JobApplication> findByJobId(Integer jobId);

    List<JobApplication> findByCandidateId(Integer candidateId);

    Optional<JobApplication> findByJobIdAndCandidateId(Integer jobId, Integer candidateId);

}

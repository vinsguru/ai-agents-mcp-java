package com.vinsguru.advisor.service;

import com.vinsguru.advisor.client.CandidateClient;
import com.vinsguru.advisor.client.CareerAdvisorClient;
import com.vinsguru.advisor.client.JobClient;
import com.vinsguru.advisor.dto.JobEvaluationResult;
import com.vinsguru.advisor.dto.JobsComparisonResult;
import com.vinsguru.advisor.dto.TailoredResume;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CareerAdvisorService {

    private final JobClient jobClient;
    private final CandidateClient candidateClient;
    private final CareerAdvisorClient advisorClient;

    public CareerAdvisorService(JobClient jobClient, CandidateClient candidateClient, CareerAdvisorClient advisorClient) {
        this.jobClient = jobClient;
        this.candidateClient = candidateClient;
        this.advisorClient = advisorClient;
    }

    public List<JobEvaluationResult> findJobs(Integer candidateId) {
        var candidate = this.candidateClient.getCandidateDetails(candidateId);
        var jobs = this.jobClient.searchBySkills(candidate.skills());
        return this.advisorClient.evaluateJobs(candidate, jobs)
                                 .stream()
                                 .sorted(Comparator.comparingInt(JobEvaluationResult::matchScore).reversed())
                                 .toList();
    }

    public JobsComparisonResult compareJobs(Integer candidateId, List<Integer> jobIds) {
        var candidate = this.candidateClient.getCandidateDetails(candidateId);
        var jobs = this.jobClient.getJobsDetails(jobIds);
        return this.advisorClient.compareJobs(candidate, jobs);
    }

    public TailoredResume generateResume(Integer candidateId, Integer jobId) {
        var candidate = this.candidateClient.getCandidateDetails(candidateId);
        var job = this.jobClient.getJobDetails(jobId);
        return this.advisorClient.generateResume(candidate, job);
    }

}

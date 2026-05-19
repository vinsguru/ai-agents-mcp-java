package com.vinsguru.hiring.service;

import com.vinsguru.hiring.client.HiringAdvisorClient;
import com.vinsguru.hiring.client.JobClient;
import com.vinsguru.hiring.dto.JobApplicationSubmittedEvent;
import com.vinsguru.hiring.mapper.EntityDtoMapper;
import com.vinsguru.hiring.repoistory.JobApplicationRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class JobApplicationEvaluationListener {

    private final JobClient jobClient;
    private final HiringAdvisorClient advisorClient;
    private final JobApplicationRepository repository;

    public JobApplicationEvaluationListener(JobClient jobClient, HiringAdvisorClient advisorClient, JobApplicationRepository repository) {
        this.jobClient = jobClient;
        this.advisorClient = advisorClient;
        this.repository = repository;
    }

    @Async
    @TransactionalEventListener
    public void handle(JobApplicationSubmittedEvent event){
        var jobApplication = this.repository.findById(event.applicationId()).orElseThrow();
        var jobDetails = this.jobClient.getJobDetails(jobApplication.getJobId());
        var evaluationRequest = EntityDtoMapper.toJobApplicationEvaluationRequest(jobApplication, jobDetails);
        var evaluationResponse = this.advisorClient.evaluate(evaluationRequest);
        jobApplication.setMatchScore(evaluationResponse.matchScore());
        jobApplication.setMatchReasoning(evaluationResponse.matchReasoning());
        this.repository.save(jobApplication);
    }

}

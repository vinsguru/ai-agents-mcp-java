package com.vinsguru.hiring.mapper;


import com.vinsguru.hiring.dto.*;
import com.vinsguru.hiring.entity.JobApplication;

public class EntityDtoMapper {

    public static JobApplication toJobApplication(JobApplicationSubmissionRequest request) {
        var entity = new JobApplication();
        entity.setJobId(request.jobId());
        entity.setCandidateId(request.candidateId());
        return entity;
    }

    public static JobApplicationDetails toJobApplicationDetails(JobApplication jobApplication) {
        return new JobApplicationDetails(
                jobApplication.getId(),
                jobApplication.getJobId(),
                jobApplication.getCandidateId(),
                jobApplication.getAppliedDate(),
                jobApplication.getResume(),
                jobApplication.getMatchScore(),
                jobApplication.getMatchReasoning()
        );
    }

    public static CandidateApplication toCandidateJobApplication(JobApplication jobApplication, JobDetails jobDetails) {
        return new CandidateApplication(
                jobApplication.getId(),
                jobApplication.getJobId(),
                jobApplication.getCandidateId(),
                jobDetails.title(),
                jobDetails.employer(),
                jobApplication.getAppliedDate(),
                jobApplication.getResume()
        );
    }

    public static JobApplicationEvaluationRequest toJobApplicationEvaluationRequest(JobApplication jobApplication, JobDetails jobDetails){
        return new JobApplicationEvaluationRequest(
                jobDetails.title(),
                jobDetails.description(),
                jobDetails.requiredSkills(),
                jobApplication.getResume()
        );
    }

}

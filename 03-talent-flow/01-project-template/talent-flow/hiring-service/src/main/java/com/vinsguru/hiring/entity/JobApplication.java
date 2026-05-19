package com.vinsguru.hiring.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class JobApplication {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer jobId;
    private Integer candidateId;
    private LocalDate appliedDate;
    private String resume;
    private Integer matchScore;
    private String matchReasoning;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Integer getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(Integer matchScore) {
        this.matchScore = matchScore;
    }

    public String getMatchReasoning() {
        return matchReasoning;
    }

    public void setMatchReasoning(String matchReasoning) {
        this.matchReasoning = matchReasoning;
    }

}

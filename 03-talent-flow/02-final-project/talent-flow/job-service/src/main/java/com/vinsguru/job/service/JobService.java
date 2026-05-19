package com.vinsguru.job.service;

import com.vinsguru.job.dto.*;
import com.vinsguru.job.mapper.EntityDtoMapper;
import com.vinsguru.job.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public JobDetails getJobById(Integer id) {
        return this.jobRepository.findById(id)
                                 .map(EntityDtoMapper::toJobDetails)
                                 .orElseThrow();
    }

    public List<JobDetails> getJobsByIds(List<Integer> ids) {
        return this.jobRepository.findAllById(ids)
                                 .stream()
                                 .map(EntityDtoMapper::toJobDetails)
                                 .toList();
    }

    public List<JobSummary> searchBySkills(List<String> skills) {
        var lowercasedSkills = skills.stream()
                                     .map(String::toLowerCase)
                                     .toList();
        return this.jobRepository.findByAnySkill(lowercasedSkills)
                                 .stream()
                                 .map(EntityDtoMapper::toJobSummary)
                                 .toList();
    }

}

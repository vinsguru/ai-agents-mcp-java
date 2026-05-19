package com.vinsguru.advisor.controller;

import com.vinsguru.advisor.dto.JobEvaluationResult;
import com.vinsguru.advisor.dto.JobsComparisonResult;
import com.vinsguru.advisor.dto.TailoredResume;
import com.vinsguru.advisor.service.CareerAdvisorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/career-advisor")
public class CareerAdvisorController {

    private static final Logger log = LoggerFactory.getLogger(CareerAdvisorController.class);
    private final CareerAdvisorService careerAdvisorService;

    public CareerAdvisorController(CareerAdvisorService careerAdvisorService) {
        this.careerAdvisorService = careerAdvisorService;
    }

    @GetMapping("/find-jobs")
    public List<JobEvaluationResult> findJobs(@RequestParam Integer candidateId) {
        log.info("Finding jobs for candidateId: {}", candidateId);
        return this.careerAdvisorService.findJobs(candidateId);
    }

    @GetMapping("/compare-jobs")
    public JobsComparisonResult compareJobs(@RequestParam Integer candidateId, @RequestParam List<Integer> jobIds) {
        log.info("Comparing jobs for candidateId: {}, jobIds: {}", candidateId, jobIds);
        return this.careerAdvisorService.compareJobs(candidateId, jobIds);
    }

    @GetMapping("/generate-resume")
    public TailoredResume generateResume(@RequestParam Integer candidateId, @RequestParam Integer jobId) {
        log.info("Generating resume for candidateId: {}, jobId: {}", candidateId, jobId);
        return this.careerAdvisorService.generateResume(candidateId, jobId);
    }

}

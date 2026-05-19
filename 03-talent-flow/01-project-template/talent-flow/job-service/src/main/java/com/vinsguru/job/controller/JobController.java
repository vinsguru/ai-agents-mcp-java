package com.vinsguru.job.controller;

import com.vinsguru.job.dto.*;
import com.vinsguru.job.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private static final Logger log = LoggerFactory.getLogger(JobController.class);
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/{id}")
    public JobDetails getJobById(@PathVariable Integer id){
        log.info("Fetching job by id: {}", id);
        return this.jobService.getJobById(id);
    }

    @GetMapping(params = "ids")
    public List<JobDetails> getJobsByIds(@RequestParam List<Integer> ids){
        log.info("Fetching jobs by ids: {}", ids);
        return this.jobService.getJobsByIds(ids);
    }

    @GetMapping(params = "skills")
    public List<JobSummary> searchBySkills(@RequestParam List<String> skills){
        log.info("Searching jobs by skills: {}", skills);
        return this.jobService.searchBySkills(skills);
    }

}

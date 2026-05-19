package com.vinsguru.candidate.controller;

import com.vinsguru.candidate.dto.CandidateDetails;
import com.vinsguru.candidate.service.CandidateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private static final Logger log = LoggerFactory.getLogger(CandidateController.class);
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/{candidateId}")
    public CandidateDetails getCandidateById(@PathVariable Integer candidateId) {
        log.info("Fetching candidate by id: {}", candidateId);
        return this.candidateService.getCandidateDetails(candidateId);
    }

}

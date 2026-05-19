package com.vinsguru.candidate.service;

import com.vinsguru.candidate.dto.CandidateDetails;
import com.vinsguru.candidate.mapper.EntityDtoMapper;
import com.vinsguru.candidate.repository.CandidateRepository;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public CandidateDetails getCandidateDetails(Integer id) {
        return this.candidateRepository.findById(id)
                                       .map(EntityDtoMapper::toCandidateDetails)
                                       .orElseThrow();
    }

}

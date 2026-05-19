package com.vinsguru.hiring.dto;

import java.time.LocalDate;

public record CandidateApplication(Integer applicationId,
                                   Integer jobId,
                                   Integer candidateId,
                                   String jobTitle,
                                   String employer,
                                   LocalDate appliedDate,
                                   String resume) {
}
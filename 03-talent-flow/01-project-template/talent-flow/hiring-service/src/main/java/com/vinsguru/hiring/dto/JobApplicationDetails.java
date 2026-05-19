package com.vinsguru.hiring.dto;

import java.time.LocalDate;

public record JobApplicationDetails(Integer applicationId,
                                    Integer jobId,
                                    Integer candidateId,
                                    LocalDate appliedDate,
                                    String resume,
                                    Integer matchScore,
                                    String matchReasoning) {
}

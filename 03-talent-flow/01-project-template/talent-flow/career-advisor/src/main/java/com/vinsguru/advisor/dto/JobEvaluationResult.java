package com.vinsguru.advisor.dto;

public record JobEvaluationResult(Integer jobId,
                                  String title,
                                  String location,
                                  String employer,
                                  Integer matchScore,
                                  String matchReasoning) {
}

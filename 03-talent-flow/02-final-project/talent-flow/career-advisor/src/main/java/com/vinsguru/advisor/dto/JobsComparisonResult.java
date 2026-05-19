package com.vinsguru.advisor.dto;

import java.util.List;

public record JobsComparisonResult(String overallVerdict,
                                   List<JobInsight> jobInsights) {

    public record JobInsight(Integer jobId,
                             String jobTitle,
                             SkillAlignment skillAlignment,
                             String skillInsight,
                             String locationInsight,
                             String salaryInsight,
                             String finalRecommendation) {
    }

    public enum SkillAlignment {
        EXCELLENT,
        GOOD,
        BAD
    }

}

package com.vinsguru.advisor.dto;

import java.util.List;

public record JobDetails(Integer id,
                         String title,
                         String description,
                         String location,
                         boolean isRemote,
                         SalaryRange salaryRange,
                         String postedDate,
                         String employer,
                         List<String> requiredSkills) {

    public record SalaryRange(Integer min,
                              Integer max) {
    }

}

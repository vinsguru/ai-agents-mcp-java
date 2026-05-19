package com.vinsguru.hiring.dto;

import java.time.LocalDate;
import java.util.List;

public record JobDetails(Integer id,
                         String title,
                         String description,
                         String location,
                         boolean isRemote,
                         SalaryRange salaryRange,
                         LocalDate postedDate,
                         String employer,
                         List<String> requiredSkills) {

    public record SalaryRange(Integer min,
                              Integer max) {
    }

}

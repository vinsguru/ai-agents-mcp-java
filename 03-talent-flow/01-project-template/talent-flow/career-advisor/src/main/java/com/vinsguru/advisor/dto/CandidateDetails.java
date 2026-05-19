package com.vinsguru.advisor.dto;

import java.time.LocalDate;
import java.util.List;

public record CandidateDetails(Integer id,
                               String name,
                               String email,
                               String phone,
                               String location,
                               Integer experienceInYears,
                               List<String> skills,
                               List<WorkExperience> workExperiences) {

    public record WorkExperience(String companyName,
                                 String jobTitle,
                                 LocalDate startDate,
                                 LocalDate endDate,
                                 String description,
                                 List<String> technologies) {
    }

}

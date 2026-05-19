package com.vinsguru.job.mapper;

import com.vinsguru.job.dto.JobDetails;
import com.vinsguru.job.dto.JobSummary;
import com.vinsguru.job.entity.Job;
import com.vinsguru.job.entity.JobSkill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityDtoMapper {

    public static JobDetails toJobDetails(Job job){
        var salaryRange = new JobDetails.SalaryRange(job.getSalaryMin(), job.getSalaryMax());
        return new JobDetails(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.isRemote(),
                salaryRange,
                job.getPostedDate(),
                job.getEmployer(),
                extractSkillNames(job.getSkills())
        );
    }

    public static JobSummary toJobSummary(Job job){
        return new JobSummary(
                job.getId(),
                job.getTitle(),
                job.getLocation(),
                job.getEmployer(),
                extractSkillNames(job.getSkills())
        );
    }

    private static List<String> extractSkillNames(List<JobSkill> skills){
        return skills.stream()
                .map(JobSkill::getSkill)
                .toList();
    }

}

package com.vinsguru.playground.sec09.server.resource;

import com.vinsguru.playground.sec09.server.service.JobService;
import com.vinsguru.playground.sec09.server.util.McpResourceUtil;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.mcp.annotation.McpResource;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JobResources {

    private final JobService jobService;

    public JobResources(JobService jobService) {
        this.jobService = jobService;
    }

    @McpResource(uri = "jobs://positions", description = "All Available Jobs")
    public McpSchema.ReadResourceResult listJobs() {
        var list = this.jobService.findAll()
                                  .stream()
                                  .map(job -> McpResourceUtil.toTextResource(JobUriFormats.BY_ID.formatted(job.id()), job.title()))
                                  .toList();
        return McpSchema.ReadResourceResult.builder(list).build();
    }

    @McpResource(uri = "jobs://positions/skill", description = "Jobs By Skill")
    public McpSchema.ReadResourceResult listSkills() {
        var list = this.jobService.getSkills()
                                  .stream()
                                  .map(skill -> McpResourceUtil.toTextResource(JobUriFormats.BY_SKILL.formatted(skill), skill))
                                  .toList();
        return McpSchema.ReadResourceResult.builder(list).build();
    }

    @McpResource(uri = "jobs://positions/location", description = "Jobs By Location")
    public McpSchema.ReadResourceResult listLocations() {
        var list = this.jobService.getLocations()
                                  .stream()
                                  .map(location -> McpResourceUtil.toTextResource(JobUriFormats.BY_LOCATION.formatted(location), location))
                                  .toList();
        return McpSchema.ReadResourceResult.builder(list).build();
    }

    @McpResource(uri = "jobs://positions/{id}")
    public McpSchema.ReadResourceResult findJobById(String id) { // it does not support Integer
        var content = McpResourceUtil.toJsonResource(
                JobUriFormats.BY_ID.formatted(id),
                this.jobService.findById(Integer.parseInt(id))
        );
        return McpSchema.ReadResourceResult.builder(List.of(content)).build();
    }

    @McpResource(uri = "jobs://positions/skill/{skill}")
    public McpSchema.ReadResourceResult listJobsBySkill(String skill) {
        var list = this.jobService.findBySkill(UriUtils.decode(skill, StandardCharsets.UTF_8)) // Cloud%20Architecture ==> Cloud Architecture
                              .stream()
                              .map(job -> McpResourceUtil.toTextResource(JobUriFormats.BY_ID.formatted(job.id()), job.title()))
                              .toList();
        return McpSchema.ReadResourceResult.builder(list).build();
    }

    @McpResource(uri = "jobs://positions/location/{location}")
    public McpSchema.ReadResourceResult listJobsByLocation(String location) {
        var list = this.jobService.findByLocation(UriUtils.decode(location, StandardCharsets.UTF_8)) // New%20York ==> New York
                              .stream()
                              .map(job -> McpResourceUtil.toTextResource(JobUriFormats.BY_ID.formatted(job.id()), job.title()))
                              .toList();
        return McpSchema.ReadResourceResult.builder(list).build();
    }

    private static class JobUriFormats {
        public static final String BY_ID = "jobs://positions/%s";
        public static final String BY_SKILL = "jobs://positions/skill/%s";
        public static final String BY_LOCATION = "jobs://positions/location/%s";
    }

}

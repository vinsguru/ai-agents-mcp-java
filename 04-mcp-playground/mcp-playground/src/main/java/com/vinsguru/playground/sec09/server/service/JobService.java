package com.vinsguru.playground.sec09.server.service;

import com.vinsguru.playground.sec09.server.dto.Job;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

// For demo
@Component
public class JobService {

    private final List<Job> jobs;
    private final List<String> skills;
    private final List<String> locations;

    public JobService(JsonMapper mapper, @Value("classpath:${section}/jobs.json") Resource resource) throws IOException {
        this.jobs = mapper.readValue(resource.getInputStream(), new TypeReference<List<Job>>() {
        });
        this.skills = this.loadSkills();
        this.locations = this.loadLocations();
    }

    public List<Job> findAll() {
        return this.jobs;
    }

    public Job findById(Integer id) {
        return this.jobs.get(id - 1);  // jobs are ordered by id.
    }

    public List<Job> findBySkill(String skill) {
        return this.jobs.stream()
                        .filter(job -> job.requiredSkills().contains(skill))
                        .toList();
    }

    public List<Job> findByLocation(String location) {
        return this.jobs.stream()
                        .filter(job -> job.location().equalsIgnoreCase(location))
                        .toList();
    }

    public List<String> getSkills() {
        return this.skills;
    }

    public List<String> getLocations() {
        return this.locations;
    }

    private List<String> loadSkills(){
        return this.jobs.stream()
                        .map(Job::requiredSkills)
                        .flatMap(Collection::stream)
                        .distinct()
                        .sorted()
                        .toList();
    }

    private List<String> loadLocations(){
        return this.jobs.stream()
                        .map(Job::location)
                        .distinct()
                        .sorted()
                        .toList();
    }

}

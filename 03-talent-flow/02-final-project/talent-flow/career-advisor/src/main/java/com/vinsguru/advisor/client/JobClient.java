package com.vinsguru.advisor.client;

import com.vinsguru.advisor.dto.JobDetails;
import com.vinsguru.advisor.dto.JobSummary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class JobClient {

    private static final String JOB_BY_ID_URI = "/api/jobs/{id}";
    private static final String JOBS_BY_IDS_URI = "/api/jobs?ids={ids}";
    private static final String JOBS_BY_SKILLS_URI = "/api/jobs?skills={skills}";
    private final RestClient restClient;

    public JobClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public JobDetails getJobDetails(Integer id) {
        return this.restClient.get()
                              .uri(JOB_BY_ID_URI, id)
                              .retrieve()
                              .body(JobDetails.class);
    }

    public List<JobDetails> getJobsDetails(List<Integer> ids) {
        return this.restClient.get()
                              .uri(JOBS_BY_IDS_URI, ids)
                              .retrieve()
                              .body(new ParameterizedTypeReference<List<JobDetails>>() {
                              });
    }

    public List<JobSummary> searchBySkills(List<String> skills) {
        return this.restClient.get()
                              .uri(JOBS_BY_SKILLS_URI, skills)
                              .retrieve()
                              .body(new ParameterizedTypeReference<List<JobSummary>>() {
                              });
    }
}

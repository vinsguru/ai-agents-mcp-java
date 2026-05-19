package com.vinsguru.hiring.client;

import com.vinsguru.hiring.dto.JobDetails;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class JobClient {

    private static final String JOB_BY_ID_URI = "/api/jobs/{id}";
    private static final String JOBS_BY_IDS_URI = "/api/jobs?ids={ids}";
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

}

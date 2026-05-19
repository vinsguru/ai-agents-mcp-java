package com.vinsguru.advisor.client;

import com.vinsguru.advisor.dto.CandidateDetails;
import org.springframework.web.client.RestClient;

public class CandidateClient {

    private static final String CANDIDATE_BY_ID_URI = "/api/candidates/{id}";
    private final RestClient restClient;

    public CandidateClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public CandidateDetails getCandidateDetails(Integer id) {
        return this.restClient.get()
                              .uri(CANDIDATE_BY_ID_URI, id)
                              .retrieve()
                              .body(CandidateDetails.class);
    }

}

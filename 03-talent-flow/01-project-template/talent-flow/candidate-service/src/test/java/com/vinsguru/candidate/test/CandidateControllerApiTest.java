package com.vinsguru.candidate.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest
@AutoConfigureRestTestClient
public class CandidateControllerApiTest {

    @Autowired
    private RestTestClient testClient;

    @Test
    public void candidateDetails() {
        this.testClient.get()
                       .uri("/api/candidates/1")
                       .exchange()
                       .expectStatus().is2xxSuccessful()
                       .expectBody()
                       .jsonPath("$.id").isEqualTo(1)
                       .jsonPath("$.name").isEqualTo("Alice Johnson")
                       .jsonPath("$.skills").isArray()
                       .jsonPath("$.experienceInYears").isEqualTo(6)
                       .jsonPath("$.workExperiences[0].companyName").isEqualTo("TechSphere Inc.")
                       .jsonPath("$.workExperiences[1].jobTitle").isEqualTo("Backend Developer");
    }

}

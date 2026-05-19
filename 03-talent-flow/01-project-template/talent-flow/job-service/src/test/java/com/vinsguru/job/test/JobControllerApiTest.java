package com.vinsguru.job.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest
@AutoConfigureRestTestClient
public class JobControllerApiTest {

    @Autowired
    private RestTestClient testClient;

    @Test
    public void jobById() {
        this.testClient.get()
                       .uri("/api/jobs/1")
                       .exchange()
                       .expectStatus().is2xxSuccessful()
                       .expectBody()
                       .jsonPath("$.id").isEqualTo(1)
                       .jsonPath("$.title").isEqualTo("Senior Software Engineer - Java")
                       .jsonPath("$.requiredSkills").isArray()
                       .jsonPath("$.isRemote").isEqualTo(true);
    }

    @Test
    public void jobsByIds() {
        this.testClient.get()
                       .uri("/api/jobs?ids=1,2")
                       .exchange()
                       .expectStatus().is2xxSuccessful()
                       .expectBody()
                       .jsonPath("$[0].id").isEqualTo(1)
                       .jsonPath("$[0].title").isEqualTo("Senior Software Engineer - Java")
                       .jsonPath("$[0].requiredSkills").isArray()
                       .jsonPath("$[0].isRemote").isEqualTo(true)
                       .jsonPath("$[1].id").isEqualTo(2)
                       .jsonPath("$[1].title").isEqualTo("Backend Developer - Golang")
                       .jsonPath("$[1].requiredSkills").isArray()
                       .jsonPath("$[1].isRemote").isEqualTo(false);
    }

    @Test
    public void jobsBySkills() {
        this.testClient.get()
                       .uri("/api/jobs?skills=redis,kafka")
                       .exchange()
                       .expectStatus().is2xxSuccessful()
                       .expectBody()
                       .jsonPath("$.length()").isEqualTo(4)
                       .jsonPath("$[0].id").isEqualTo(3)
                       .jsonPath("$[0].title").isEqualTo("Platform Engineer - Java")
                       .jsonPath("$[1].id").isEqualTo(4)
                       .jsonPath("$[1].title").isEqualTo("Software Engineer II (Golang)")
                       .jsonPath("$[2].id").isEqualTo(9)
                       .jsonPath("$[2].title").isEqualTo("Java Microservices Developer")
                       .jsonPath("$[3].id").isEqualTo(10)
                       .jsonPath("$[3].title").isEqualTo("Senior Software Engineer - Go");
    }

}

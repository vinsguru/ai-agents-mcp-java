package com.vinsguru.hiring.config;

import com.vinsguru.hiring.client.HiringAdvisorClient;
import com.vinsguru.hiring.client.JobClient;
import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.json.JsonMapper;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public JobClient jobClient(RestClient.Builder builder, @Value("${job-service.url}") String baseUrl) {
        var restClient = builder.baseUrl(baseUrl).build();
        return new JobClient(restClient);
    }

    @Bean
    public HiringAdvisorClient hiringAdvisorClient(ChatClient.Builder builder, JsonMapper jsonMapper, ResourceLoader resourceLoader) {
        var chatClient = builder.defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                                .defaultSystem(resourceLoader.getResource("classpath:prompt-templates/system.txt"))
                                .defaultUser(resourceLoader.getResource("classpath:prompt-templates/user.txt"))
                                .build();
        return new HiringAdvisorClient(chatClient, jsonMapper);
    }

}

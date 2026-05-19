package com.vinsguru.advisor.config;

import com.vinsguru.advisor.client.CandidateClient;
import com.vinsguru.advisor.client.CareerAdvisorClient;
import com.vinsguru.advisor.client.JobClient;
import com.vinsguru.advisor.dto.CareerAdvisorPrompts;
import com.vinsguru.advisor.dto.PromptSet;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.nio.charset.Charset;

@Configuration
public class ApplicationConfiguration {

    private static final String USER_TEMPLATE_PATH_FORMAT = "classpath:prompt-templates/%s/user.txt";
    private static final String SYSTEM_TEMPLATE_PATH_FORMAT = "classpath:prompt-templates/%s/system.txt";

    private final ResourceLoader resourceLoader;

    public ApplicationConfiguration(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public JobClient jobClient(RestClient.Builder builder, @Value("${job-service.url}") String baseUrl){
        var restClient = builder.baseUrl(baseUrl).build();
        return new JobClient(restClient);
    }

    @Bean
    public CandidateClient candidateClient(RestClient.Builder builder, @Value("${candidate-service.url}") String baseUrl){
        var restClient = builder.baseUrl(baseUrl).build();
        return new CandidateClient(restClient);
    }

    @Bean
    public CareerAdvisorClient careerAdvisorClient(ChatClient.Builder builder, JsonMapper jsonMapper){
        var chatClient = builder.build();
        var prompts = new CareerAdvisorPrompts(
                this.getPromptSet("compare-jobs"),
                this.getPromptSet("evaluate-jobs"),
                this.getPromptSet("generate-resume")
        );
        return new CareerAdvisorClient(chatClient, prompts, jsonMapper);
    }

    private PromptSet getPromptSet(String feature){
        return new PromptSet(
                this.getResourceContent(SYSTEM_TEMPLATE_PATH_FORMAT.formatted(feature)),
                this.getResourceContent(USER_TEMPLATE_PATH_FORMAT.formatted(feature))
        );
    }

    private String getResourceContent(String resourcePath){
        try{
            var resource = this.resourceLoader.getResource(resourcePath);
            return resource.getContentAsString(Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

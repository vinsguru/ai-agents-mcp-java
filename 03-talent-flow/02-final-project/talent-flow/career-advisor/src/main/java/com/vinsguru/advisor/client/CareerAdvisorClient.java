package com.vinsguru.advisor.client;

import com.vinsguru.advisor.dto.*;
import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

public class CareerAdvisorClient {

    private static final String JOB = "job";
    private static final String JOBS = "jobs";
    private static final String CANDIDATE = "candidate";

    private final ChatClient chatClient;
    private final CareerAdvisorPrompts prompts;
    private final JsonMapper jsonMapper;

    public CareerAdvisorClient(ChatClient chatClient, CareerAdvisorPrompts prompts, JsonMapper jsonMapper) {
        this.chatClient = chatClient;
        this.prompts = prompts;
        this.jsonMapper = jsonMapper;
    }

    public List<JobEvaluationResult> evaluateJobs(CandidateDetails candidate, List<JobSummary> jobs) {
        return this.chatClient.prompt()
                              .advisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                              .system(this.prompts.evaluateJobs().system())
                              .user(spec -> spec.text(this.prompts.evaluateJobs().user())
                                                .param(CANDIDATE, this.toJsonString(candidate))
                                                .param(JOBS, this.toJsonString(jobs)))
                              .call()
                              .entity(new ParameterizedTypeReference<List<JobEvaluationResult>>() {
                              });
    }

    public JobsComparisonResult compareJobs(CandidateDetails candidate, List<JobDetails> jobs) {
        return this.chatClient.prompt()
                              .advisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                              .system(this.prompts.compareJobs().system())
                              .user(spec -> spec.text(this.prompts.compareJobs().user())
                                                .param(CANDIDATE, this.toJsonString(candidate))
                                                .param(JOBS, this.toJsonString(jobs)))
                              .call()
                              .entity(JobsComparisonResult.class);
    }

    public TailoredResume generateResume(CandidateDetails candidate, JobDetails job) {
        var resume = this.chatClient.prompt()
                              .system(this.prompts.generateResume().system())
                              .user(spec -> spec.text(this.prompts.generateResume().user())
                                                .param(CANDIDATE, this.toJsonString(candidate))
                                                .param(JOB, this.toJsonString(job)))
                              .call()
                              .content();
        return new TailoredResume(job.id(), candidate.id(), resume);
    }

    // Providing data as structured JSON ensures the model correctly maps attributes between the candidate and job requirements.
    private String toJsonString(Object object) {
        return this.jsonMapper.writeValueAsString(object);
    }

}

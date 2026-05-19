package com.vinsguru.hiring.service;

import com.vinsguru.hiring.dto.JobApplicationSubmittedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationEvaluationListener {

    @Async
    @EventListener
    public void handle(JobApplicationSubmittedEvent event){

    }

}

package com.vinsguru.playground.sec07.host.dto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class NotificationChannel {

    private final Sinks.Many<UserNotification> sink;
    private final Flux<UserNotification> flux;

    public NotificationChannel(Sinks.Many<UserNotification> sink, Flux<UserNotification> flux) {
        this.sink = sink;
        this.flux = flux;
    }

    public void emit(UserNotification value){
        this.sink.emitNext(value, Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(1)));
    }

    public Flux<UserNotification> stream(String progressToken){
        return this.flux.filter(notification -> notification.progressToken().equals(progressToken));
    }

}

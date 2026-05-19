package com.vinsguru.playground.sec08.host.dto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class NotificationChannel<T extends NotificationEvent> {

    private final Sinks.Many<T> sink;
    private final Flux<T> flux;

    public NotificationChannel(Sinks.Many<T> sink, Flux<T> flux) {
        this.sink = sink;
        this.flux = flux;
    }

    public Flux<T> stream(String progressToken){
        return this.flux.filter(notification -> notification.progressToken().equals(progressToken));
    }


    public void emit(T value){
        this.sink.emitNext(value, Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(1)));
    }
}

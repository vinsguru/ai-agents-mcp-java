package com.vinsguru.agent.dto;

public sealed interface ToolResult<T> {

    record Success<T>(T data) implements ToolResult<T> {

    }

    record Failure<T>(String errorMessage) implements ToolResult<T> {

    }

    static <T> ToolResult<T> success(T data){
        return new Success<>(data);
    }

    static <T> ToolResult<T> failure(Throwable throwable){
        return new Failure<>(throwable.getMessage());
    }

}
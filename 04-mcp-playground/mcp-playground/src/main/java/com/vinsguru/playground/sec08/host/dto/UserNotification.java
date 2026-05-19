package com.vinsguru.playground.sec08.host.dto;

import java.util.Map;

public record UserNotification(String progressToken,
                              String message,
                              Map<String, Object> formSchema) implements NotificationEvent {
}

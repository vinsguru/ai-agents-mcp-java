package com.vinsguru.playground.sec03.server.tools;

import com.vinsguru.playground.sec03.server.dto.Reminder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ReminderTools {

    private static final Logger log = LoggerFactory.getLogger(ReminderTools.class);
    private final List<Reminder> reminders = new CopyOnWriteArrayList<>(); // thread safe

    @McpTool(name = "create-reminder", description = "Create a reminder with task and scheduled date-time")
    public void createReminder(Reminder reminder) {
        log.info("request: {}", reminder);
        reminders.add(reminder);
    }

    @McpTool(name = "list-reminders", description = "List all scheduled reminders")
    public List<Reminder> listReminders() {
        log.info("listing reminders");
        return List.copyOf(reminders);
    }

}

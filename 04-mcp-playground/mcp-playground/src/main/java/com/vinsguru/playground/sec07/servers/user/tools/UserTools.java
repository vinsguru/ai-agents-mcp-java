package com.vinsguru.playground.sec07.servers.user.tools;

import com.vinsguru.playground.sec07.servers.user.dto.Address;
import com.vinsguru.playground.sec07.servers.user.dto.FlightPreference;
import com.vinsguru.playground.sec07.servers.user.dto.UserProfile;
import org.springframework.ai.mcp.annotation.McpMeta;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.context.McpSyncRequestContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserTools {

    private final Map<Integer, UserProfile> userProfiles = Map.of(
            1, new UserProfile(1, "Sam", new Address("123 north street", "Atlanta", "Georgia"), FlightPreference.CHEAP_PRICE),
            2, new UserProfile(2, "Mike", new Address("456 south street", "San Francisco", "California"), FlightPreference.SHORT_DURATION)
    );

    @McpTool(description = "Get user profile and travel preferences")
    public UserProfile getUserProfile(McpSyncRequestContext context, McpMeta meta){
        var userId = (int) meta.get("userId");
        context.progress(spec -> spec.message("fetching user profile for user id %d".formatted(userId)));
        return this.userProfiles.get(userId);
    }

}

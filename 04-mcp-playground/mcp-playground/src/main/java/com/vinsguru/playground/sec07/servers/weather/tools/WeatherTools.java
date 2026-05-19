package com.vinsguru.playground.sec07.servers.weather.tools;

import com.vinsguru.playground.sec07.servers.weather.dto.WeatherForecast;
import com.vinsguru.playground.sec07.servers.weather.dto.WeatherRequest;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.context.McpSyncRequestContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class WeatherTools {

    private static final List<String> CONDITIONS = List.of(
            "Sunny", "Partly Cloudy", "Cloudy", "Rainy", "Windy", "Clear", "Scattered Thunderstorms"
    );

    @McpTool(description = "Get weather forecast for a specific airport and time.")
    public WeatherForecast getWeatherForecast(McpSyncRequestContext context, WeatherRequest request){
        context.progress(spec -> spec.message("fetching weather forecast for %s on %s".formatted(request.airportCode(), request.arrivalTime().toLocalDate())));
        var temperature = ThreadLocalRandom.current().nextInt(50, 101); // Fahrenheit (50–100)
        var condition = CONDITIONS.get(ThreadLocalRandom.current().nextInt(CONDITIONS.size()));
        return new WeatherForecast(temperature, condition);
    }

}

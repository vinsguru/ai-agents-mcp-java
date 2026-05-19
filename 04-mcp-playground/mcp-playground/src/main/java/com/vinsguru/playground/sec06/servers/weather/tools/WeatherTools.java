package com.vinsguru.playground.sec06.servers.weather.tools;

import com.vinsguru.playground.sec06.servers.weather.dto.WeatherForecast;
import com.vinsguru.playground.sec06.servers.weather.dto.WeatherRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class WeatherTools {

    private static final Logger log = LoggerFactory.getLogger(WeatherTools.class);

    private static final List<String> CONDITIONS = List.of(
            "Sunny", "Partly Cloudy", "Cloudy", "Rainy", "Windy", "Clear", "Scattered Thunderstorms"
    );

    @McpTool(description = "Get weather forecast for a specific airport and time.")
    public WeatherForecast getWeatherForecast(WeatherRequest request){
        log.info("fetching weather forecast for {}", request);
        var temperature = ThreadLocalRandom.current().nextInt(50, 101); // Fahrenheit (50–100)
        var condition = CONDITIONS.get(ThreadLocalRandom.current().nextInt(CONDITIONS.size()));
        return new WeatherForecast(temperature, condition);
    }

}

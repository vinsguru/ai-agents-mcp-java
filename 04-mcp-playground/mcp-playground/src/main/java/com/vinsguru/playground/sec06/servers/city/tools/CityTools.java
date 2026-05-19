package com.vinsguru.playground.sec06.servers.city.tools;

import com.vinsguru.playground.sec06.servers.city.dto.City;
import com.vinsguru.playground.sec06.servers.city.dto.Recommendation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CityTools {

    private static final Logger log = LoggerFactory.getLogger(CityTools.class);
    private Map<String, String> codeToCityMap;
    private Map<String, Recommendation> codeToRecommendationMap;

    public CityTools(JsonMapper mapper, @Value("classpath:${section}/city-data.json") Resource resource) throws IOException {
        this.initialize(mapper, resource);
    }

    @McpTool(description = "Get city name and airport code mapping")
    public Map<String, String> getAirportCodes() {
        log.info("fetching airport codes");
        return this.codeToCityMap;
    }

    @McpTool(description = "Get local recommendations by airport code")
    public Recommendation getRecommendations(@McpToolParam(description = "airport code (e.g., JFK)") String airportCode) {
        log.info("fetching recommendations for airport code: {}", airportCode);
        return this.codeToRecommendationMap.get(airportCode);
    }

    private void initialize(JsonMapper mapper, Resource resource) throws IOException {
        var cities = mapper.readValue(resource.getInputStream(), new TypeReference<List<City>>() {
        });
        this.codeToCityMap = cities.stream()
                                   .collect(Collectors.toMap(
                                                City::airportCode,
                                                City::name
                                        ));
        this.codeToRecommendationMap = cities.stream()
                                             .collect(Collectors.toMap(
                                                          City::airportCode,
                                                          City::recommendation
                                                  ));
    }

}

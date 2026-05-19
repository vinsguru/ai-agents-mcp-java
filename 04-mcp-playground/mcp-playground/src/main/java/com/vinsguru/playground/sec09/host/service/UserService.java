package com.vinsguru.playground.sec09.host.service;

import com.vinsguru.playground.sec09.host.dto.UserProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    // User profiles in String format for demo
    private final Map<Integer, String> users;

    public UserService(JsonMapper mapper, @Value("classpath:${section}/users.json") Resource resource) throws IOException {
        this.users = this.loadUserProfiles(mapper, resource);
    }

    public String getUserProfile(Integer userId){
        return this.users.get(userId);
    }

    private Map<Integer, String> loadUserProfiles(JsonMapper mapper, Resource resource) throws IOException {
        return mapper.readValue(resource.getInputStream(), new TypeReference<List<UserProfile>>() {
                     })
                     .stream()
                     .collect(Collectors.toMap(
                             UserProfile::id,
                             mapper::writeValueAsString
                     ));
    }

}

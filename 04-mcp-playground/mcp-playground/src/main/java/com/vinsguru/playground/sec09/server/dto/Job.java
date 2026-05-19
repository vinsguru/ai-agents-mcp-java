package com.vinsguru.playground.sec09.server.dto;

import java.util.List;

public record Job(Integer id,
                  String title,
                  String description,
                  String location,
                  String employer,
                  List<String> requiredSkills) {
}
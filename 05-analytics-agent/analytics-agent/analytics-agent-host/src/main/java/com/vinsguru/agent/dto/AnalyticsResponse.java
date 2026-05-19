package com.vinsguru.agent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

public record AnalyticsResponse(String insight,
                                String chartTitle,
                                ChartType chartType,
                                DataContainer resultData) {

        @JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_VALUES)
        public enum ChartType {
            BAR, LINE, PIE, DOUGHNUT
        }

        public record DataContainer(List<String> keys,
                                    List<Series> values) {
        }

        public record Series(String name,
                             List<Number> points) {
        }

}

/*
        Example:

        keys = ["Jan", "Feb", "Mar"]

        values = [
           {
              name = "Orders",
              points = [120, 140, 180]
           },
           {
              name = "Returns",
              points = [10, 15, 20]
           }
        ]

* */
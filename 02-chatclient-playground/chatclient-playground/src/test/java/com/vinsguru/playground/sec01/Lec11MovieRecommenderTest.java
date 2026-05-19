package com.vinsguru.playground.sec01;

import com.vinsguru.playground.AbstractTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Lec11MovieRecommenderTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec11MovieRecommenderTest.class);

    record Movie(String title,
                 Double imdbRating,
                 Integer releaseYear) {

    }

    @BeforeAll
    public void setup() {
        var systemPrompt = """
                You are a movie recommendation assistant.
                
                Rules:
                - Recommend only well-known movies.
                - Do not recommend more than 3 movies.
                """;
        var userPrompt = """
                Suggest movies similar to {movieTitle}.
                """;
        this.chatClient = this.builder.defaultSystem(systemPrompt)
                                      .defaultUser(userPrompt)
                                      .build();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "pulp fiction",
            "inception",
            "the good, the bad, the ugly"
    })
    public void recommendMovies(String movieTitle) {
        recommendSimilarMovies(movieTitle)
                .forEach(movie -> log.info("{}", movie));
    }

    private List<Movie> recommendSimilarMovies(String movieTitle){
        var movies = this.chatClient.prompt()
                                    .advisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                                    .user(template -> template.param("movieTitle", movieTitle))
                                    .call()
                                    .entity(new ParameterizedTypeReference<List<Movie>>() {
                                    });
        return Objects.requireNonNullElse(movies, Collections.emptyList());
    }

}

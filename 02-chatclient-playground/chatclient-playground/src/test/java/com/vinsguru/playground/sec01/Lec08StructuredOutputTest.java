package com.vinsguru.playground.sec01;

import com.vinsguru.playground.AbstractTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class Lec08StructuredOutputTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec08StructuredOutputTest.class);

    record Book(String title,
                String author,
                int publishedYear) {
    }

    @Test
    public void mapEntityFromText() {
        var prompt = """
                Extract book details from the below text.
                
                I just finished reading "Clean Code" by John.
                The copyright page says it was printed in 2015.
                """;
        var book = this.executePrompt(prompt)
                       .entity(Book.class);
        log.info("{}", book);
    }

    /*
     * OpenAI does not support top-level array types
     * Use wrapper type as a workaround: record Books(List<Book> list) {}
     * */
    @Test
    public void mapEntitiesFromText() {
        var prompt = """
                Extract book details from the below text.
                
                Sam published a book on Java Programming in 2016.
                He also released another book on Python Programming, 3 years later.
                That same year Mike released a book on Reactive Programming.
                """;
        var books = this.executePrompt(prompt)
                        .entity(new ParameterizedTypeReference<List<Book>>() {
                        });
        books.forEach(book -> log.info("{}", book));
    }

    private ChatClient.CallResponseSpec executePrompt(String prompt) {
        return this.chatClient.prompt(prompt)
                              .advisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                              .call();
    }

}

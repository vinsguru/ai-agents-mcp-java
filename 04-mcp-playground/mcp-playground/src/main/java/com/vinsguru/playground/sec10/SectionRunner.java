package com.vinsguru.playground.sec10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class SectionRunner {

    @SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.host")
    public static class Host {

        static void main(String[] args) {
            SpringApplication.run(Host.class, "--section=sec10", "--config=host");
        }

    }

    @SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.server")
    public static class Server {

        static void main(String[] args) {
            SpringApplication.run(Server.class,  "--section=sec10", "--config=server");
        }

    }

}

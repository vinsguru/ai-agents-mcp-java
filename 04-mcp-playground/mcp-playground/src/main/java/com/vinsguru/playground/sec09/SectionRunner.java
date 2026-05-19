package com.vinsguru.playground.sec09;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class SectionRunner {

    @SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.host")
    public static class Host {

        static void main(String[] args) {
            SpringApplication.run(Host.class, "--section=sec09", "--config=host");
        }

    }

    @SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.server")
    public static class Server {

        static void main(String[] args) {
            SpringApplication.run(Server.class,  "--section=sec09", "--config=server");
        }

    }

}

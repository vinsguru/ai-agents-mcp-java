package com.vinsguru.playground.sec04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


public class SectionRunner {

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.host")
	static class Host {

		static void main(String[] args) {
			SpringApplication.run(Host.class, "--section=sec04", "--config=host");
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.server")
	static class Server {

		static void main(String[] args) {
			SpringApplication.run(Server.class,  "--section=sec04", "--config=server", "--version=v3");
		}

	}

}

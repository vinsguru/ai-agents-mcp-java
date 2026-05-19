package com.vinsguru.playground.sec06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


public class SectionRunner {

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.host")
	static class Host {

		static void main(String[] args) {
			SpringApplication.run(Host.class, "--section=sec06", "--config=host");
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.servers.city")
	static class CityService {

		static void main(String[] args) {
			SpringApplication.run(CityService.class,  "--section=sec06", "--config=city-service");
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.servers.flight")
	static class FlightService {

		static void main(String[] args) {
			SpringApplication.run(FlightService.class,  "--section=sec06", "--config=flight-service");
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.servers.user")
	static class UserService {

		static void main(String[] args) {
			SpringApplication.run(UserService.class,  "--section=sec06", "--config=user-service");
		}

	}

	@SpringBootApplication(scanBasePackages = "com.vinsguru.playground.${section}.servers.weather")
	static class WeatherService {

		static void main(String[] args) {
			SpringApplication.run(WeatherService.class,  "--section=sec06", "--config=weather-service");
		}

	}

}

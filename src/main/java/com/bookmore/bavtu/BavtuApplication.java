package com.bookmore.bavtu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class BavtuApplication {

	public static void main(String[] args) {
		SpringApplication.run(BavtuApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		Duration TIMEOUT = Duration.ofSeconds(20L);
		return builder.setConnectTimeout(TIMEOUT)
				.build();
	}

}

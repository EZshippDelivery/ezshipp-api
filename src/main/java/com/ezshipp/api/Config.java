package com.ezshipp.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("spring-datasource")
@SuppressWarnings("unused")
public class Config {

	@Profile("dev")
	@Bean
	public String devProfile() {
		return "Dev Profile Active";
	}
	
	@Profile("test")
	@Bean
	public String testProfile() {
		return "Test Profile Active";
	}
	
	@Profile("prod")
	@Bean
	public String prodProfile() {
		return "Prod Profile Active";
	}
}

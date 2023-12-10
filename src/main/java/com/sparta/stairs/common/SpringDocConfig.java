package com.sparta.stairs.common;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
				.group("sparta")
				.pathsToMatch("/api/**")
				.build();
	}
}

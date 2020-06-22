package com.ssafy.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Value("${resources.user_location}")
	private String resourcesLocation;

	@Value("${resources.user_uri_path}")
	private String resourcesUriPath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(resourcesUriPath + "/**").addResourceLocations("file:///" + resourcesLocation);
	}

//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		// 모든 uri에 대해 http://localhost:8080, http://localhost:8082 도메인은 접근을 허용한다.
//		registry.addMapping("/**").allowedOrigins("https://localhost:8080", "https://localhost:8082");
//	}
}

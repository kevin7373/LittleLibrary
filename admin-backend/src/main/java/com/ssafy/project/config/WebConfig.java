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
		registry.addResourceHandler(resourcesUriPath + "/**")
		.addResourceLocations("file:///" + resourcesLocation);
	}
}

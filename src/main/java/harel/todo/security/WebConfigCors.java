package harel.todo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebSecurity
public class WebConfigCors 
extends WebMvcConfigurationSupport
{
	
	@Override
	protected void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")  
        .allowedMethods("POST", "GET", "OPTIONS", "DELETE", "PUT", "HEAD");

		
		
	}

}

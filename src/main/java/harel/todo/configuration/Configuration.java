package harel.todo.configuration;

import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@org.springframework.context.annotation.Configuration
public class Configuration {
	
	

		@Bean
		public PasswordEncoder getPasswordEncoder() {
			return new BCryptPasswordEncoder();
		}
	

}

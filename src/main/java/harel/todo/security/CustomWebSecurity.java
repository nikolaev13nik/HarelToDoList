package harel.todo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import harel.todo.dao.UserRepository;

@Component("customWebSecurity")
public class CustomWebSecurity {
	
	@Autowired
	UserRepository userRepository;
	
	public boolean checkAuthorityAndloginInPass(String login,Authentication authentication) {		
		return login.equals(authentication.getName());
	}

	
}


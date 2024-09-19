package harel.todo.controller;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import harel.todo.dto.UserEditDto;
import harel.todo.dto.UserProfileDto;
import harel.todo.dto.UserRegisterDto;
import harel.todo.service.UserAccountService;


@RestController
@RequestMapping("/account")
public class UserAccountController {
	
	@Autowired
	UserAccountService userAccountService;
	
	@PostMapping("/user")
	public UserProfileDto register(@RequestBody UserRegisterDto userRegisterDto) {
		return userAccountService.register(userRegisterDto);
	}
	
	@PostMapping("/login")
	public UserProfileDto login(Authentication authentication) {
		return userAccountService.login(authentication.getName());
	}

	@DeleteMapping("/user/{login}")
	public UserProfileDto removeUser(@PathVariable String login) {
		return userAccountService.removeUser(login);
	}
	
	@PutMapping("/user/password/{login}")
	public UserProfileDto editUser(@RequestBody UserEditDto userEditDto,
									@PathVariable String login
			) {
		return userAccountService.editUser(login, userEditDto);
	}
	

	@GetMapping("/users")
	public List<UserProfileDto> getAllUsers(){
		return userAccountService.getAllUsers();
	}

}

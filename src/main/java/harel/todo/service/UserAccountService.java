package harel.todo.service;

import java.util.List;

import harel.todo.dto.UserEditDto;
import harel.todo.dto.UserProfileDto;
import harel.todo.dto.UserRegisterDto;



public interface UserAccountService {
	
	UserProfileDto register(UserRegisterDto userRegisterDto);
	
	UserProfileDto login(String login);
	
	UserProfileDto editUser(String login, UserEditDto userEditDto);
	
	UserProfileDto removeUser(String login);
	

	List<UserProfileDto>getAllUsers();
}

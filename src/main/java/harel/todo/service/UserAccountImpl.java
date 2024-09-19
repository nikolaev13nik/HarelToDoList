package harel.todo.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import harel.todo.dao.UserRepository;
import harel.todo.dto.TaskDto;
import harel.todo.dto.UserEditDto;
import harel.todo.dto.UserProfileDto;
import harel.todo.dto.UserRegisterDto;
import harel.todo.exceptions.UserExistsException;
import harel.todo.exceptions.UserNotFoundException;
import harel.todo.model.Task;
import harel.todo.model.User;

@Service
public class UserAccountImpl implements UserAccountService{
	
	@Autowired
	UserRepository accountRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserProfileDto register(UserRegisterDto userRegisterDto) {
		if (accountRepository.existsById(userRegisterDto.getLogin())) {
			throw new UserExistsException();
		}
		String hashPassword = passwordEncoder.encode(userRegisterDto.getPassword());
		User user=new User(userRegisterDto.getLogin(),userRegisterDto.getName(),hashPassword);
		accountRepository.save(user);
		return userToUserProfileDto(user);
	}
	private UserProfileDto userToUserProfileDto(User userAccount) {
		return UserProfileDto.builder()
				.login(userAccount.getLogin())
				.name(userAccount.getName())
				.roles(userAccount.getRoles())
				.tasks(convertTasksToTasksDto(userAccount.getTasks()))
				.build();
	}

	private List<TaskDto> convertTasksToTasksDto(List<Task> tasks) {
		return tasks.stream().map(t->convertTaskToDto(t)).collect(Collectors.toList());
	}
	
	private TaskDto convertTaskToDto(Task t) {
		return  TaskDto.builder()
				.createdDate(t.getCreatedDate())
				.isDone(t.getIsDone())
				.id(t.getId())
				.modifiedDate(t.getModifiedDate())
				.title(t.getTitle())
				.build();
	}
	@Override
	public UserProfileDto login(String login) {
		User user = accountRepository.findById(login).get();		
		return userToUserProfileDto(user);
	}

	@Override
	public UserProfileDto editUser(String login, UserEditDto userEditDto) {
		User user = accountRepository.findById(login).get();	
		if (userEditDto.getName() != null) {
			user.setName(userEditDto.getName());
		}
		if (userEditDto.getPassword()!=null) {
			String hashPassword = passwordEncoder.encode(userEditDto.getPassword());
			user.setPassword(hashPassword);
		}
		accountRepository.save(user);
		return userToUserProfileDto(user);
	}

	@Override
	public UserProfileDto removeUser(String login) {
		User userAccount = accountRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		accountRepository.deleteById(login);
		return userToUserProfileDto(userAccount);
	}


	@Override
	public List<UserProfileDto> getAllUsers() {
		List<User>listUsers=accountRepository.findAll();
		return listUsers.stream().map(dto->userToUserProfileDto(dto)).collect(Collectors.toList());
	}
}

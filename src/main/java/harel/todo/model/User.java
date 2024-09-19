package harel.todo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

@ToString
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "login")
@Document(collection = "users")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	String login;
	String name;
	String password;
	@Singular
	List<Task> tasks;
	@Singular
	Set<String> roles;

	
	
	public boolean addRole(String role) {
		return roles.add(role);
	}

	public boolean removeRole(String role) {
		return roles.remove(role);
	}

	public boolean addTask(Task task) {
		return tasks.add(task);
	}

	public boolean removeTask(Task task) {
		return tasks.remove(task);
	}

	public User(String login, String name, String password) {
		validatorLogin(login);
		validatorPassword(password);
		this.login = login;
		this.name = name;
		this.password = password;
		this.tasks = new ArrayList<>();
		Set<String>set=new HashSet<String>();
		set.add("User");
		this.roles = set;
	}
	private void validatorPassword(String password) {
		if (password.length()==0) {
			throw new IllegalArgumentException("password is not correct");
		}
		
	}

	private void validatorLogin(String login) {
		if (login.length()==0) {
			throw new IllegalArgumentException("login is not correct");
		}
		
	}

}

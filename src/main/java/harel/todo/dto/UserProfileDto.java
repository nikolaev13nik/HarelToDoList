package harel.todo.dto;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserProfileDto {
	
	String login;
	String name;
	List<TaskDto> tasks;
	Set<String>roles;

}
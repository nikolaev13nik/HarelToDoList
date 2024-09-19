package harel.todo.service;

import java.util.List;
import harel.todo.dto.EditTaskDto;
import harel.todo.dto.TaskDto;

public interface TaskService {
	
	
	List<TaskDto> createTask(TaskDto taskDto,String login);
	
	List<TaskDto> getAllTasks(String login);
	
	List<TaskDto> updateTask(EditTaskDto editTaskDto,String login);
	
	List<TaskDto> deleteTask(String login,String idTask);
	
	List<TaskDto> deleteAllTasks(String login);
	
	

}

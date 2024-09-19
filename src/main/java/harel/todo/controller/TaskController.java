package harel.todo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import harel.todo.dto.EditTaskDto;
import harel.todo.dto.TaskDto;
import harel.todo.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {
	
	@Autowired
	TaskService taskService;
	
	@PutMapping("/{login}")
	public  List<TaskDto> createTask(@RequestBody TaskDto taskDto,@PathVariable String login) {
		return taskService.createTask(taskDto, login);
	}
	
	@GetMapping("/{login}")
	public	List<TaskDto>getAllTask(@PathVariable String login){
		return taskService.getAllTasks(login);
	}
	
	
	@PutMapping("/update/{login}")
	public	List<TaskDto>updateTask(@PathVariable String login,@RequestBody EditTaskDto editTaskDto){
		return taskService.updateTask(editTaskDto, login);
	}
	
	@DeleteMapping("/{login}/{idTask}")
	public	List<TaskDto>deleteTask(@PathVariable String login,@PathVariable String idTask){
		return taskService.deleteTask(login, idTask);
	}
	
	@PostMapping("/clear/tasks/{login}")
	public List<TaskDto> deleteAllTasks(@PathVariable String login){
		return taskService.deleteAllTasks(login);
	}
	

	
}

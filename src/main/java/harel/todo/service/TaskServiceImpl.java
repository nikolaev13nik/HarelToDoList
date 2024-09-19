package harel.todo.service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import com.mongodb.client.result.UpdateResult;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import harel.todo.dao.UserRepository;
import harel.todo.dto.EditTaskDto;
import harel.todo.dto.TaskDto;
import harel.todo.exceptions.NotFoundTaskException;
import harel.todo.exceptions.UserNotFoundException;
import harel.todo.model.Task;
import harel.todo.model.User;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	UserRepository accountRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<TaskDto> createTask(TaskDto taskDto, String login) {
		Task task = convertTaskDtoToTask(taskDto);	
		Query query = new Query().addCriteria(Criteria.where("_id").is(login));
		Update update = new Update().push("tasks", task);
		mongoTemplate.updateFirst(query, update, User.class);
		User user =accountRepository.findById(login).orElseThrow(()->new UserNotFoundException(login));	
		return user.getTasks().stream().map(t->convertTaskToTaskDto(t)).collect(Collectors.toList());
				
	}

	private TaskDto convertTaskToTaskDto(Task task) {
		return TaskDto.builder()
				.createdDate(task.getCreatedDate())
				.id(task.getId())
				.isDone(task.getIsDone())
				.modifiedDate(task.getModifiedDate())
				.title(task.getTitle())
				.build();
	}

	private Task convertTaskDtoToTask(TaskDto dto) {
		return new Task( dto.getTitle(), LocalDateTime.now(), LocalDateTime.now(), false);
	}

	@Override
	public List<TaskDto> getAllTasks(String login) {
		UnwindOperation unwind=Aggregation.unwind("tasks");
		Aggregation aggregation=Aggregation.newAggregation(
				match(Criteria.where("_id").is(login)),
				unwind,
				project("tasks._id","tasks.isDone","tasks.modifiedDate",
						"tasks.title","tasks.createdDate")
				);
		List<Task> result= mongoTemplate.aggregate(aggregation, "users",Task.class).getMappedResults();
		return convertTasksToTasksDto(result);
	}

	private List<TaskDto> convertTasksToTasksDto(List<Task> result) {
		return result.stream().map(t->convertTaskToTaskDto(t)).collect(Collectors.toList());
	}

	@Override
	public List<TaskDto> updateTask(EditTaskDto editTaskDto, String login) {
		ObjectId idTask=new ObjectId(editTaskDto.getId());
		Query query=new Query();
		query.addCriteria(Criteria.where("_id").is(login).andOperator(Criteria.where("tasks").elemMatch(Criteria.where("_id").is(idTask))));
		Update update = new Update();
		if (editTaskDto.getTitle()!=null) {
			update.set("tasks.$.title", editTaskDto.getTitle());
		}
		update.set("tasks.$.modifiedDate", LocalDateTime.now());
		update.set("tasks.$.isDone",editTaskDto.getIsDone());
		mongoTemplate.updateFirst(query, update, User.class);
		User user=mongoTemplate.findOne(query, User.class);
		return convertTasksToTasksDto(new ArrayList<Task>(user.getTasks()));
		
	}

	@Override
	public List<TaskDto> deleteTask(String login, String idTask) {
		ObjectId id=new ObjectId(idTask);
		Query query = new Query().addCriteria(Criteria.where("_id").is(login));		
		UpdateResult  res=mongoTemplate.updateMulti(query,
		        new Update().pull("tasks", Query.query(Criteria.where("_id").is(id))), "users");	
		if (res.getModifiedCount()==0) {
			throw  new NotFoundTaskException("Not exist comment or post by id", null);
		}
			User user =mongoTemplate.findOne(query, User.class);
		return	convertTasksToTasksDto(user.getTasks());
	}

	@Override
	public List<TaskDto> deleteAllTasks(String login) {
		Query query=new Query();
		query.addCriteria(Criteria.where("_id").is(login));
		Update update = new Update();	
			update.set("tasks", new ArrayList<>());		
		mongoTemplate.updateFirst(query, update, User.class);
		User user=mongoTemplate.findOne(query, User.class);
		return convertTasksToTasksDto(new ArrayList<Task>(user.getTasks()));
	}
}

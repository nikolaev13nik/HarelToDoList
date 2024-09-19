package harel.todo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import harel.todo.model.User;

public interface UserRepository extends MongoRepository<User, String>{

}

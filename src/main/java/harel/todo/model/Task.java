package harel.todo.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

@ToString
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class Task implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	String id;
	String title;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime createdDate;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	
	LocalDateTime modifiedDate;
	Boolean isDone;
	
	public Task(String title, LocalDateTime createdDate, LocalDateTime modifiedDate, Boolean isDone) {
		
		this.id = new ObjectId().toHexString();
		this.title = title;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.isDone = isDone;
	}
	
	
	

}

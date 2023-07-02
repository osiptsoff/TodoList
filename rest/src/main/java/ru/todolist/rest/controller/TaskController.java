package ru.todolist.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ru.todolist.rest.model.Task;
import ru.todolist.rest.service.TaskService;

class NotFoundException extends RuntimeException { private static final long serialVersionUID = 1L; }

@ApiResponses(value = {
		@ApiResponse(responseCode = "403", description = "Request is unathorized. If no message then no token in header is present;"
				+ "if token is invalid,'Token is invalid' message will be in body.",
				content = @Content(schema = @Schema(implementation = Void.class))),
		@ApiResponse(responseCode = "500", description = "Exception while database querying.",
				content = @Content(schema = @Schema(implementation = Void.class)))
})
@RestController
@RequestMapping(path = "rest/users/tasks")
public class TaskController {	
	@Autowired
	private TaskService taskService;
	
	@Operation(summary = "Get list of tasks of currently authorized user.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Returns all tasks of user fitting given parameters.",
			content = { @Content(mediaType = "application/json", 
			array = @ArraySchema(schema = @Schema(implementation = Task.class))) }),
		@ApiResponse(responseCode = "404", description = "No requested resources found.",
				content = @Content(schema = @Schema(implementation = Void.class)))
	})
	@GetMapping()
	public List<Task> getTasks(@RequestParam(required = false) Boolean actual,
			@RequestParam(required = false) String date) {
		List<Task> tasks = taskService.getTasks(getCurrentUserId(), actual, date);
		
		if(tasks.isEmpty())
			throw new NotFoundException();
		
		return tasks;
	}
	
	@Operation(summary = "Adds task to currently authorized user.")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Succsess."),
		@ApiResponse(responseCode = "400", description = "One of task fields is incorrect. It is date often, it must be 'YYYY-MM-DD'")
	})
	@PostMapping()
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addTask(@RequestBody Task task) {
		taskService.addTask(getCurrentUserId(), task);
	}
	
	@Operation(summary = "Removes task with specified id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Succsess."),
			@ApiResponse(responseCode = "404", description = "No requested resources found.")
	})
	@DeleteMapping("/{idtask}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeTask(@PathVariable int idtask) {
		int rowsAffected = taskService.removeTask(getCurrentUserId(), idtask);
		if(rowsAffected == 0)
			throw new NotFoundException();
	}
	
	@Operation(summary = "Modifies task with specified id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Succsess."),
			@ApiResponse(responseCode = "404", description = "No requested resources found.")
	})
	@PutMapping("/{idtask}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void modifyTask(@PathVariable int idtask, @RequestBody Task task) {
		task.setIdtask(idtask);
		int rowsAffected = taskService.modifyTask(getCurrentUserId(), idtask, task);
		if(rowsAffected == 0)
			throw new NotFoundException();
	}
	
	private Integer getCurrentUserId() {
		return (Integer)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}

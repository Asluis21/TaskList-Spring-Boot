package com.aluis.springboot.task.list.app.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aluis.springboot.task.list.app.models.entities.Task;
import com.aluis.springboot.task.list.app.models.service.ITaskService;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"http://localhost:4200"}) 
@RestController
public class TaskController {

	@Autowired
	private ITaskService service;
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm"; // PARA EL LINK (LA RUTA<)
	
	private static final int PAGE_SIZE = 5;
	
	
	@GetMapping("/tasks")
	public ResponseEntity<?> listTasks(){
		
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			List<Task> tasks = service.findAll();
			if (tasks.isEmpty()) {
				response.put("tasks", tasks);
				response.put("message", "No tasks");
				status = HttpStatus.NO_CONTENT;
			}else{
				response.put("tasks", tasks);
				response.put("message", "Tasks found successfully");
			}
			
		}catch (Exception e) {
			response.put("message", "Failed to find tasks");
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<Map<String, Object>>(response, status);
	}
	
	/*
	@GetMapping("/tasksPageable")
	public ResponseEntity<?> listTasksPageable(@RequestParam(defaultValue = "0") int page){
		
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		try {
			
			Pageable pageable = PageRequest.of(page, PAGE_SIZE);
			Page<Task> tasks = service.findAll(pageable);
			response.put("tasks", tasks);
			if (tasks.isEmpty()) {
				response.put("message", "No tasks");
				status = HttpStatus.NOT_FOUND;
			}else{
				response.put("message", "Tasks found successfully");
			}
			
			
		}catch(Exception e){
			response.put("message", "Falied to find tasks");
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<Map<String, Object>>(response, status);
	}*/
	
	@GetMapping("/tasksPageable") //@GetMapping("/taskName")
	public ResponseEntity<?> taskName(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") int page){
		
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			response.put("title", "All tasks");
			
			Pageable pageable = PageRequest.of(page, PAGE_SIZE);
			Page<Task> tasks = service.findTasksByNameContaining(keyword, pageable);
			response.put("tasks", tasks);
			
			if(tasks.isEmpty()) {
				response.put("message", "No tasks");
				status = HttpStatus.NOT_FOUND;			
			}else {
				response.put("message", "Task found successfully");
			}
			
		}catch (Exception e) {
			response.put("message", "Failed to find tasks");
		}
		return new ResponseEntity<Map<String, Object>>(response, status);
	}
		
	@GetMapping("/overdueTasks")
	public ResponseEntity<?> overdueTasks(@RequestParam(required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date date){
		
		if (date == null) {
			date = new Date();
		}
		
		Map<String, Object> response = new HashMap<>(); 
		HttpStatus status = HttpStatus.OK;
		
		try {
			
			List<Task> overdueTasks = service.findByCompletedFalseAndDueDateBefore(date);
			response.put("tasks", overdueTasks);
			if (overdueTasks.isEmpty()) {
				response.put("message", "No overdue tasks");
				status = HttpStatus.NOT_FOUND;
			}else{
				response.put("message", "Tasks found successfully");
			}
			
			
		}catch (Exception e) {
			response.put("message", "Failed to find pending tasks");
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<Map<String, Object>>(response, status);
		
	}
	
	@GetMapping("/overdueTasksPageable")
	public ResponseEntity<?> overdueTasksPageable(@RequestParam(defaultValue = "") String keyword, @RequestParam(required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date date,
			@RequestParam(defaultValue = "0") int page){
		
		if (date == null) {
			date = new Date();
		}
		
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			response.put("title", "Overdue tasks");
			
			Pageable pageable = PageRequest.of(page, PAGE_SIZE);	
			//Page<Task> overdueTasks = service.findByCompletedFalseAndDueDateBefore(date, pageable);
			Page<Task> overdueTasks = service.findByNameContainingAndCompletedFalseAndDueDateBefore(keyword, date, pageable);
			response.put("tasks", overdueTasks);
			if (overdueTasks.isEmpty()) {
				response.put("message", "No overdue tasks");
				status = HttpStatus.NOT_FOUND;
			}else{
				response.put("message", "Tasks found successfully");
			}
			
			
		}catch (Exception e) {
			response.put("message", "Failed to find overdue tasks");
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<Map<String, Object>>(response, status);
	}
	
	@GetMapping("/pendingTasks")
	public ResponseEntity<?> pendingTasks(@RequestParam(required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date date){
		
		if (date == null) {
			date = new Date();
		}
		
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			response.put("title", "Pending Tasks");
			
			List<Task> pendingtasks = service.findByCompletedFalseAndDueDateAfter(date);
			response.put("tasks", pendingtasks);
			if (pendingtasks.isEmpty()) {
				response.put("message", "No pending tasks");
				status = HttpStatus.NOT_FOUND;
			}else{
				response.put("message", "Tasks found successfully");
			}
			
		}catch (Exception e) {
			response.put("message", "Fail to find overdue tasks");
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<Map<String, Object>>(response, status);
	}
	
	@GetMapping("/pendingTasksPageable")
	public ResponseEntity<?> pendingTasksPageable(@RequestParam(defaultValue = "") String keyword, @RequestParam(required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date date,
			@RequestParam(defaultValue = "0") int page){
		
		if (date == null) {
			date = new Date();
		}
		
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			response.put("title", "Pending Tasks");
			
			Pageable pageable = PageRequest.of(page, PAGE_SIZE);
			//Page<Task> pendingtasks = service.findByCompletedFalseAndDueDateAfter(date, pageable);
			Page<Task> pendingtasks = service.findByNameContainingAndCompletedFalseAndDueDateAfter(keyword, date, pageable);
			response.put("tasks", pendingtasks);
			if (pendingtasks.isEmpty()) {
				response.put("message", "No pending tasks");
				status = HttpStatus.NOT_FOUND;
			}else{
				response.put("message", "Tasks found successfully");
			}
			
		}catch (Exception e) {
			response.put("message", "Failed to find overdue tasks");
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<Map<String, Object>>(response, status);
	}
	
	@GetMapping("/completeTasks")
	public ResponseEntity<?> completeTasks(){
		
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			
			List<Task> completeTasks = service.findByCompletedTrue();
			
			response.put("tasks", completeTasks);
			if (completeTasks.isEmpty()) {
				response.put("message", "No complete tasks");
				status = HttpStatus.NOT_FOUND;
				
			}else {
				response.put("message", "Tasks found successfully");
			}
			
			
		}catch (Exception e) {
			response.put("message", "Faield to find complete tasks");
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<Map<String, Object>>(response, status);
	}
	
	@GetMapping("/completeTasksPageable")
	public ResponseEntity<?> completeTasksPageable(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") int page){
		
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			response.put("title", "Completed tasks");
			
			Pageable pageable = PageRequest.of(page, PAGE_SIZE);
			
			//Page<Task> completeTasks = service.findByCompletedTrue(pageable);
			Page<Task> completeTasks = service.findByNameContainingAndCompletedTrue(keyword, pageable);
			response.put("tasks", completeTasks);
			
			if (completeTasks.isEmpty()) {
				
				response.put("message", "No complete tasks");
				status = HttpStatus.NOT_FOUND;
				
			}else {
				response.put("message", "Tasks found successfully");
			}
			
			
		}catch (Exception e) {
			response.put("message", "Faield to find complete tasks");
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<Map<String, Object>>(response, status);
	}
	
	@GetMapping("/tasks/{id}")
	public ResponseEntity<?> findTaskById(@PathVariable Long id){
		
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			Task taskFound = service.findById(id);
			
			System.out.println(taskFound);
			
			response.put("tasks", taskFound);
			
			
			if(taskFound == null) {
				response.put("message", "Task not found");
				status = HttpStatus.NOT_FOUND;
			}else {
				response.put("message", "Task found successfully");
			}
			
		}catch (Exception e) {
			response.put("message", "Failed to find task by id");
			status = HttpStatus.BAD_REQUEST;
		}
		
		
		return new ResponseEntity<Map<String, Object>>(response, status);
	}
	
	
	@DeleteMapping("/deleteTask/{id}")
	public ResponseEntity<?> deleteTask(@PathVariable Long id){
		
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {

			Task taskFound = service.findById(id);
			response.put("tasks", taskFound);
			
			if(taskFound == null) {
				
				response.put("message", "Task not deleted");
				status = HttpStatus.NOT_FOUND;
				
				
			}else {
				service.deleteTaskById(id);
				response.put("message", "Task delete successfully");
			}
			
			
		}catch (Exception e) {
			response.put("message", "Failed to delete task by id");
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<Map<String, Object>>(response, status);
	}
	
	@DeleteMapping("/deleteTasks")
	public ResponseEntity<?> DeleteTasks(@RequestParam List<Long> ids){
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			
			boolean atLeastOneTaskExists = service.existsAnyTaskWithId(ids);
			
			if (atLeastOneTaskExists) {
				service.deleteMulpliesTaskByIds(ids);
				response.put("message", "Tasks deleted successfully");
			}else {
				response.put("message", "There are any task available to delete");
				status = HttpStatus.NOT_FOUND;
			}

			
		}catch (Exception e) {
			response.put("message", "There are no task available to delete");
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<Map<String, Object>>(response, status);
	}
	
	@PostMapping("/tasks")
	public ResponseEntity<?> createTask(@Valid @RequestBody Task task, BindingResult result){
		
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = HttpStatus.CREATED;
		
		if (task.getDueDate() != null && task.getDueDate().before(task.getCreateAt())) {
			result.rejectValue("dueDate", "dueDate.beforeCreateAt", "Due date must be greater than create date");
		}		
		
		try {
			
			if (result.hasErrors()) {
				
				Map<String, String> errors = new HashMap<>();
				
				
				result.getFieldErrors().forEach(err ->{
					errors.put(err.getField(), err.getDefaultMessage());
				});
				
				
				response.put("message", errors);
				status = HttpStatus.BAD_REQUEST;
			}else {
				
				Task taskCreate = service.saveTask(task);
				

				
				response.put("task", taskCreate);			
				response.put("message", "Task created sucessfully");
			}
		} catch (Exception e) {
	
			response.put("error", e.getMessage());
			response.put("message", "Failed to save a task");
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<Map<String, Object>> (response, status);
	}
	
	
	@PutMapping("/tasks/{id}")
	public ResponseEntity<?> updateTask(@PathVariable long id, @Valid @RequestBody Task task, BindingResult result){
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		if (task.getDueDate() != null && task.getDueDate().before(task.getCreateAt())) {
			result.rejectValue("dueDate", "dueDate.beforeCreateAt", "Due date must be greater than create date");
		}
		
		try {
			
			if(result.hasErrors()) {
				Map<String, String> errors = new HashMap<>();
				
				result.getFieldErrors().forEach(err ->{
					errors.put(err.getField(), err.getDefaultMessage());
				});
				
				status = HttpStatus.BAD_REQUEST;
				
				response.put("message", errors);
			}else {
				Task taskFind = service.findById(id);
				if (taskFind != null) {
					taskFind.setName(task.getName());
					taskFind.setCompleted(task.getCompleted());
					taskFind.setCreateAt(task.getCreateAt());
					taskFind.setDescription(task.getDescription());
					taskFind.setDueDate(task.getDueDate());
					//taskFind.setPriority(task.getPriority());
					taskFind.setPriorityId(task.getPriorityId());
					
					response.put("task", service.saveTask(taskFind));
					
				}else {
					response.put("message", "Cannot find the task");
					status= HttpStatus.NOT_FOUND;
				}	
			}
			
		}catch (Exception e) {
			response.put("message", "Failed to update the task");
			status= HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<Map<String, Object>>(response, status);
	}
}

package com.aluis.springboot.task.list.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aluis.springboot.task.list.app.models.entities.Priority;
import com.aluis.springboot.task.list.app.models.service.ITaskService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/priority")
public class PriorityController {

	@Autowired
	private ITaskService service;
	
	@GetMapping()
	public ResponseEntity<?> findAllPriorities(){
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			
			List<Priority> listPrioritiesFounded = service.findAllPriorities();
			System.out.println(service.findAllPriorities());
			
			if(listPrioritiesFounded.isEmpty()) {
				status = HttpStatus.NOT_FOUND;
				response.put("message", "No priorities found");
			}else {
				response.put("priorities", listPrioritiesFounded);
				response.put("message", "Priorities found successfully");
			}
		}catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			response.put("message", "Something went wrong");
		}
		return new ResponseEntity<Map<String, Object>>(response, status);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findPriorityById(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			
			Priority priorityFound = service.findPriorityById(id);
			
			if(priorityFound == null) {
				status = HttpStatus.NOT_FOUND;
				response.put("message", "No priority found");
				
			}else {
				response.put("priority", priorityFound);
				response.put("message", "Priority found successfully");
			}
			
		}catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			response.put("message", "Something went wrong");
		}
		
		return new ResponseEntity<Map<String, Object>>(response, status);
	}
}

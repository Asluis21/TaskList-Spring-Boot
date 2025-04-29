package com.aluis.springboot.task.list.app.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aluis.springboot.task.list.app.models.entities.Priority;
import com.aluis.springboot.task.list.app.models.entities.Task;

public interface ITaskService {

	List<Task> findAll(); // -
	
	Page<Task> findAll(Pageable pageable); // -
	
	Task findById(Long id); //- 
	
	Task saveTask(Task task);  
	
	void deleteTaskById(Long id); //-
	
	boolean existsAnyTaskWithId(List<Long> ids);
	
	void deleteMulpliesTaskByIds(List<Long> ids);
	
	List<Task> findByCompletedFalseAndDueDateAfter(Date date); // -
	
	List<Task> findByCompletedFalseAndDueDateBefore(Date date); // -
	
	List<Task> findByCompletedTrue();  // -
	
	Page<Task> findByCompletedFalseAndDueDateAfter(Date date, Pageable pageable); // - OVERDUE
	
	Page<Task> findByCompletedFalseAndDueDateBefore(Date date, Pageable pageable); // - PENDING 
	
	Page<Task> findByCompletedTrue(Pageable pageable);  // -
	
	// ====== FOR BAR SEARCH ======
	
	Page<Task> findByNameContainingAndCompletedTrue(String keyword, Pageable pageable);  // -
	
	Page<Task> findByNameContainingAndCompletedFalseAndDueDateAfter(String keyword,Date date, Pageable pageable); // - OVERDUE
	
	Page<Task> findByNameContainingAndCompletedFalseAndDueDateBefore(String keyword, Date date, Pageable pageable); // - PENDING
	
	Page<Task> findTasksByNameContaining(String keyword, Pageable pageable);
	
	// ============ Priority =========== 
	
	List<Priority> findAllPriorities();
	
	Priority findPriorityById(Long id);
}

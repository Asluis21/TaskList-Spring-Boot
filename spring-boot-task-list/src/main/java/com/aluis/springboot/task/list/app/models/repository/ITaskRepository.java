package com.aluis.springboot.task.list.app.models.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aluis.springboot.task.list.app.models.entities.Task;

public interface ITaskRepository extends JpaRepository<Task, Long>{
	
	List<Task> findByCompletedFalseAndDueDateAfter(Date date);
	
	List<Task> findByCompletedFalseAndDueDateBefore(Date date);
	
	List<Task> findByCompletedTrue();
	
	//  ========== FOR BAR SEARCH ==========
	
	Page<Task> findTasksByNameContaining(String keyword, Pageable pageable);
	
	Page<Task> findByNameContainingAndCompletedTrue(String keyword, Pageable pageable);  // -
	
	Page<Task> findByNameContainingAndCompletedFalseAndDueDateAfter(String keyword,Date date, Pageable pageable); // - OVERDUE
			   
	Page<Task> findByNameContainingAndCompletedFalseAndDueDateBefore(String keyword, Date date, Pageable pageable); // - PENDING

	boolean existsByIdIn(List<Long> ids);
}

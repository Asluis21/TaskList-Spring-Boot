package com.aluis.springboot.task.list.app.models.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aluis.springboot.task.list.app.models.entities.Priority;
import com.aluis.springboot.task.list.app.models.entities.Task;
import com.aluis.springboot.task.list.app.models.repository.IPriorityRepository;
import com.aluis.springboot.task.list.app.models.repository.ITaskRepository;

@Service
public class TaskServiceImpl implements ITaskService {

	@Autowired
	private ITaskRepository repository;
	
	@Autowired
	private IPriorityRepository priorityRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Task> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Task> findAll(Pageable pageable) {
		
		List<Task> tasks = findAll();
		List<Task> pageTasks = getPageTasks(tasks, pageable);
		
		return new PageImpl<>(pageTasks, pageable, tasks.size()); //repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Task findById(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Task saveTask(Task task) {
		return repository.save(task);
	}

	@Override
	@Transactional
	public void deleteTaskById(Long id) {
		
		repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)// COMPLETADO FALSO Y VENCIMIENTO DESPUES DE LA FECHA = VENCIDO
	public List<Task> findByCompletedFalseAndDueDateAfter(Date date) {
		return repository.findByCompletedFalseAndDueDateAfter(date);
	}

	@Override
	@Transactional(readOnly = true) // COMPLETADO FALSO Y VENCIMIENTO ANTES DE LA FECHA = PENDIENTE
	public List<Task> findByCompletedFalseAndDueDateBefore(Date date) {
		return repository.findByCompletedFalseAndDueDateBefore(date);
	}

	@Override
	@Transactional(readOnly = true) // COMPLETADO 
	public List<Task> findByCompletedTrue() {
		return repository.findByCompletedTrue();
	}

	@Override
	@Transactional(readOnly = true) // COMPLETADO FALSO Y VENCIMIENTO DESPUES DE LA FECHA = VENCIDO
	public Page<Task> findByCompletedFalseAndDueDateAfter(Date date, Pageable pageable) {
		
		List<Task> tasks = findByCompletedFalseAndDueDateAfter(date);
		List<Task> pageTasks = getPageTasks(tasks, pageable);
		
		return new PageImpl<>(pageTasks, pageable, tasks.size());
	}

	@Override
	@Transactional(readOnly = true) // COMPLETADO FALSO Y VENCIMIENTO ANTES DE LA FECHA  = PENDIENTE
	public Page<Task> findByCompletedFalseAndDueDateBefore(Date date, Pageable pageable) {
		
		List<Task> tasks = findByCompletedFalseAndDueDateBefore(date);
		List<Task> pageTasks = getPageTasks(tasks, pageable);
		
		return new PageImpl<>(pageTasks, pageable, tasks.size());
	}

	@Override
	@Transactional(readOnly = true) // COMPLETADO
	public Page<Task> findByCompletedTrue(Pageable pageable) {
		
		List<Task> tasks = findByCompletedTrue();
		List<Task> pageTasks = getPageTasks(tasks, pageable);
		
		return new PageImpl<>(pageTasks, pageable, tasks.size());
	}
	
	
	
	private List<Task> getPageTasks(List<Task> tasks, Pageable pageable){
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize; 
		
		if(tasks.size() < startItem) {
			return Collections.emptyList();
		}
		
		int toIndex = Math.min(startItem + pageSize, tasks.size());
		return tasks.subList(startItem, toIndex);
	}

	@Override
	@Transactional
	public void deleteMulpliesTaskByIds(List<Long> ids) {
		repository.deleteAllByIdInBatch(ids);
		
	}

	@Override
	@Transactional
	public boolean existsAnyTaskWithId(List<Long> ids) {
		return repository.existsByIdIn(ids);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Priority> findAllPriorities() {
		return priorityRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Priority findPriorityById(Long id) {
		return priorityRepository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Task> findTasksByNameContaining(String keyword, Pageable pageable) {
		
		return repository.findTasksByNameContaining(keyword, pageable); 
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Task> findByNameContainingAndCompletedTrue(String keyword, Pageable pageable) {
		return repository.findByNameContainingAndCompletedTrue(keyword, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Task> findByNameContainingAndCompletedFalseAndDueDateAfter(String keyword, Date date,
			Pageable pageable) {
		
		return repository.findByNameContainingAndCompletedFalseAndDueDateAfter(keyword, date, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Task> findByNameContainingAndCompletedFalseAndDueDateBefore(String keyword, Date date,
			Pageable pageable) {
		
		return repository.findByNameContainingAndCompletedFalseAndDueDateBefore(keyword, date, pageable);
	}
}

package com.aluis.springboot.task.list.app.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aluis.springboot.task.list.app.models.entities.Priority;

public interface IPriorityRepository extends JpaRepository<Priority, Long>{

}

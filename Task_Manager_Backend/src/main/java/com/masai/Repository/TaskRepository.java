package com.masai.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.Entity.Task;

public interface TaskRepository extends JpaRepository<Task,Integer>{
	List<Task> findByUserId(int userId);
}

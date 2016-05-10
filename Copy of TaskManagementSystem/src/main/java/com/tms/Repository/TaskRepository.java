package com.tms.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tms.Entity.Comments;
import com.tms.Entity.Task;
import com.tms.Entity.User;
public interface TaskRepository extends JpaRepository<Task, Long> {



	Task findByComment(Comments comment);

	public Page<Task> findByUser(User user, Pageable pageRequest);
	
	
}

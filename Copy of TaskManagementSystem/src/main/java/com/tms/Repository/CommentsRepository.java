package com.tms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tms.Entity.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

	
	//public List<Comments> findByTask(Task taskId);
}

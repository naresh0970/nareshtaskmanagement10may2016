package com.tms.Repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.tms.Entity.Task;
import com.tms.Entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByUserName(String name);
	User findByTask(Task task);

	 @Query(value="select user from User user where user.name LIKE :name||'%'")
	 public List<User> findCustomBybyName(@Param("name") String name);


	 
}


package com.tms.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tms.Entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

	 @Query(value="select tag from Tag tag where tag.tagName LIKE :tagname||'%'")
	 public List<Tag> findbyTagName(@Param("tagname") String tagname);


	
	


}

package com.main;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class controller{

		@Autowired
		repository r;
		
	@RequestMapping("/adduser")
	public void addStudent(@RequestBody user user){
		r.save(user);
	

	}
}
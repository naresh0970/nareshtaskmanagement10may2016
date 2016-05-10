package com.tms.Controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tms.MyUserDetials;
import com.tms.Entity.Comments;
import com.tms.Entity.Module;
import com.tms.Entity.Project;
import com.tms.Entity.Tag;
import com.tms.Entity.Task;
import com.tms.Entity.User;
import com.tms.Repository.ProjectRepository;
import com.tms.Repository.TaskRepository;
import com.tms.Repository.UserRepository;
import com.tms.Repository.CommentsRepository;
import com.tms.Repository.ModuleRepository;
import com.tms.Repository.TagRepository;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;





import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class TaskController {

	@Autowired
	MyUserDetials myUserDetails;

	@Autowired
	CommentsRepository commentsRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommentsRepository commentRepository;

	@Autowired
	ModuleRepository moduleRepository;


	@Autowired
	TaskRepository taskRepository;
	@Autowired
	TagRepository tagRepository;

	@Autowired
	ProjectRepository projectRepository;
	
	private int pageCount;

	@RequestMapping("/registerUser")
	public void registeruser(@RequestBody User user){

		userRepository.save(user);
	}

	@RequestMapping("/login")
	public HashMap < String, Object > getLogin() {



		HashMap < String, Object > returnParams = new HashMap < String, Object > ();

		if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) && SecurityContextHolder.getContext().getAuthentication().isAuthenticated() == true) {
			returnParams.put("status", "true");
			returnParams.put("user", SecurityContextHolder.getContext().getAuthentication().getName());
			returnParams.put("userId", ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
			returnParams.put("role", ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole());

			Collection < ? extends GrantedAuthority > authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			Boolean authority = authorities.contains(new SimpleGrantedAuthority("USER"));
			
			Boolean auth = true;

			if (authority == auth){
				returnParams.put("status", "true");
			} else {

			}
		}
		return returnParams;
	}
	@RequestMapping(value = "/getUsers")
	public @ResponseBody Page<User> getUsers(@RequestBody HashMap<String , Integer> map) {
		int pageCount= map.get("pageCount");
		int pageSize=map.get("pageSize");
		PageRequest pageRequest=new PageRequest(pageCount,pageSize);

		return  (Page <User>)userRepository.findAll(pageRequest);
	}

	@RequestMapping("/addtask")
	public void addtask(@RequestBody Task task){
		List<Tag> tags = task.getTag();
		for (Tag tag : tags) {
			if(tag.id == null){
				tagRepository.save(tag);
			}
		}
		task = taskRepository.save(task);
	}

	//@JsonView(UserView.user.class)
	@RequestMapping("/getCurrentUserTask")
	public Page<Task> getTask(@RequestParam("ps")int pageSize,@RequestParam("pc")int pc, @RequestParam("u") long userId){
		if(pc == 0){
			pageCount=0;
		}
		else{
			pageCount=pc-1;
		}
		PageRequest pageRequest=new PageRequest(pageCount,pageSize);
		User user = userRepository.findOne(userId);
		return taskRepository.findByUser(user,pageRequest);


	}
	@RequestMapping("/getAllTasks")
	public List<Task> getAllTasks(){
		
		return  taskRepository.findAll();
	}
	@RequestMapping("/getAllTask")
	public Page<Task> getAllTask(@RequestParam("ps")int pageSize,@RequestParam("pc")int pc){
		if(pc == 0){
			pageCount=0;
		}
		else{
			pageCount=pc-1;
		}
		PageRequest pageRequest=new PageRequest(pageCount,pageSize);
		return  (Page <Task>)taskRepository.findAll(pageRequest);
	}

	@RequestMapping("/addComment")
	public void addComment(@RequestBody Comments comment){
		commentRepository.save(comment);
	}

	@RequestMapping("/getComments")
	public List<Comments> comment(@RequestParam("value") Long taskId){
		Task t = taskRepository.findOne(taskId);
		return t.getComment();
	}

	@RequestMapping("/loadTags")
	public List < Tag > getTags(@RequestParam("value") String tagName) {
		return tagRepository.findbyTagName(tagName + "%");
	}
	
	@RequestMapping("/getProjects")
	public List<Project> getProject(){
		return projectRepository.findAll();
	}

	@RequestMapping("/loadUsers")
	public List < User > loadUsers(@RequestParam("value") String name) {
		return userRepository.findCustomBybyName(name + "%");
	}
	
	@RequestMapping("/addModule")
	public void addModule(@RequestBody Module module){
		moduleRepository.save(module);
	}
	
	@RequestMapping("/getModule")
	public List<Module> getModule(){
		return moduleRepository.findAll();
	}
}
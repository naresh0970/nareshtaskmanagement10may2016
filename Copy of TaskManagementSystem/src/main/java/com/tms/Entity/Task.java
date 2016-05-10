package com.tms.Entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


@Entity
@Table(name="task")
public class Task {
	
	public enum Type{
		TASK,ISSUE
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="taskId")
	private long taskId;
	
	@Column(name="title")
	private String title;
	
	@Column(name="startdate")
	Date startDate=new Date();
	
	
	@Column(name="duedate")
	Date endDate=new Date();
	
	@Column(name="description")
	private String description;
	
	@Column(name="assigner")
	public String assigner;

	@Column(name="status")
	private String status;
	
	@Column(name="document")
	@Lob
    private String document;	
	
	public String getDocument() {
		return document;
	}


	public void setDocument(String document) {
		this.document = document;
	}


	@Enumerated(EnumType.STRING)
	@Column(name="type")
	private Type type;
	
	
	 
	public String getAssigner() {
		return assigner;
	}


	public void setAssigner(String assigner) {
		this.assigner = assigner;
	}



	public Project getProject() {
		return project;
	}


	public void setProject(Project project) {
		this.project = project;
	}


	@OneToMany(mappedBy="task",fetch=FetchType.LAZY)
	List<Comments> comment;
	
	@ManyToOne
	@JoinColumn(name="project")
	Project project;
	
	
	@ManyToOne
	@JoinColumn(name="user")
	User user;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY,cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinTable(name="task_tag",
            joinColumns=@JoinColumn(name="taskId",referencedColumnName="taskId"),
            inverseJoinColumns=@JoinColumn(name="tagid", referencedColumnName="tagid")
    )
    private List<Tag> tag;       
	
	
	@ManyToOne
	@JoinColumn(name="module")
	private Module module;



	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Comments> getComment() {
		return comment;
	}


	public void setComment(List<Comments> comment) {
		this.comment =  comment;
	}
	public long getTaskId() {
		return taskId;
	}


	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User users) {
		this.user = users;
	}


	public List<Tag> getTag() {
		return tag;
	}

	@JsonProperty(access = Access.WRITE_ONLY)
	public void setTag(List<Tag> tags) {
		this.tag = tags;
	}


	public Module getModule() {
		return module;
	}


	public void setModule(Module module) {
		this.module = module;
	}
	


	
	/*public boolean isEdited() {
  		
  		
  		boolean edited;
		if ((SecurityContextHolder.getContext().getAuthentication().getName()).equalsIgnoreCase(this.users.userName))
  			edited= true;
  		else
  			edited=false;
  		return edited;
  	}*/



}

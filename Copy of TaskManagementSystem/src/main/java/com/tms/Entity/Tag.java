package com.tms.Entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


@Entity
public class Tag {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="tagid")
	public Integer id;

	@Column(name="tagname")
	public String tagName;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name="task_tag",
    joinColumns= @JoinColumn(name="tagid",referencedColumnName="tagid"),
    inverseJoinColumns= @JoinColumn(name="taskId", referencedColumnName="taskId"))
	List<Task> task;

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public List<Task> getTask() {
		return task;
	}

	@JsonProperty(access = Access.WRITE_ONLY)
	public void setTask(List<Task> task) {
		this.task = task;
	}

	
	
}

package com.project.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@DynamicUpdate
@Entity
@Component
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Department {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = Instructor.class, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@JoinColumn(name = "di_fk", referencedColumnName = "id")
	@Column(updatable = false)
	private List<Instructor> instructors = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, targetEntity = Courses.class, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@JoinColumn(name = "dc_fk", referencedColumnName = "id")
	@Column(updatable = false)
	private List<Courses> courses = new ArrayList<>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Instructor> getInstructors() {
		return instructors;
	}
	public void addInstructors(Instructor instructor) {
		this.instructors.add(instructor);
	}
	public List<Courses> getCourses() {
		return courses;
	}
	public void addCourses(Courses course) {
		this.courses.add(course);
	}
	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]\n";
	}
}
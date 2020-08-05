package com.project.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.demo.model.Courses;

public interface CoursesService {
	//get all courses
	public List<Courses> getCourses();
	
	//search for a course by id
	public Courses get(int id);
	
	//get course by name in pages
	public Page<Courses> get(String name, Pageable pageable);
	
	//get all courses in pages
	public List<Courses> getAll(Pageable pageable);

	//search for a course by name
	public List<Courses> get(String name);
	
	//search for a course by name descending
	public List<Courses> getDesc(String name);
	
	//add a new course
	public String save(@Autowired Courses course, int iId, int dId);
	
	//update a course
	public String update(@Autowired Courses course);
	
	//delete a course
	public String delete(int id);
}
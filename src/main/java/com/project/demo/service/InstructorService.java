package com.project.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.demo.model.Instructor;

public interface InstructorService {
	//get all instructors
	public List<Instructor> getInstructors();
	
	//get instructor by name in pages
	public Page<Instructor> get(String name, Pageable pageable);
	
	//get all instructors in pages
	public List<Instructor> getAll(Pageable pageable);
	
	//search for an instructor by name
	public List<Instructor> get(String name);
	
	//search for an instructor by name descending
	public List<Instructor> getDesc(String name);
	
	//search for an instructor by id
	public Instructor get(int id);
	
	//save an instructor with address and department
	public String save(@Autowired Instructor instructor, int aId, int dId);
	
	//update an instructor
	public String update(@Autowired Instructor instructor);
	
	//delete an instructor
	public String delete(int id);
}
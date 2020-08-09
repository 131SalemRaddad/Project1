package com.project.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.demo.model.BachelorStudent;
import com.project.demo.model.Courses;

public interface BachelorStudentService {
	//get all bachelor students
	public List<BachelorStudent> getBachelorStudents();
	
	//get bachelor student by name in pages
	public Page<BachelorStudent> get(String name, Pageable pageable);
	
	//get all bachelor students in pages
	public List<BachelorStudent> getAll(Pageable pageable);
	
	//get one bachelor student
	public BachelorStudent get(int id);
	
	//search for a bachelor student by name
	public List<BachelorStudent> get(String name);
	
	//search for a bachelor student by name descending
	public List<BachelorStudent> getDesc(String name);
	
	//save a bachelor student with address and course
	public String save(@Autowired BachelorStudent bachelorStudent, int aId, int cId);
	
	//save a bachelor student without course
	public String save(@Autowired BachelorStudent bachelorStudent, int aId);
	
	//add a course to a bachelor student
	public String save(int id, int cId);
	
	//update a bachelor student
	public String update(@Autowired BachelorStudent bachelorStudent);
	
	//delete a bachelor student
	public String delete(int id);
	
	//drop a course from student's courses
	public String drop(int id, int cId);
	
	//delete all bachelor students
	public String deleteAll();
	
	//get all courses based on name
	public List<Courses> getCoursesByName(String name);
}
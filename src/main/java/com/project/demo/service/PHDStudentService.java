package com.project.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.demo.model.PHDStudent;

public interface PHDStudentService {
	//get all PHD students
	public List<PHDStudent> getPHDStudents();
	
	//get PHD student by name in pages
	public Page<PHDStudent> get(String name, Pageable pageable);
	
	//get all PHD students in pages
	public List<PHDStudent> getAll(Pageable pageable);
	
	//search for a PHD student by id
	public PHDStudent get(int id);
	
	//search for a PHD student by name
	public List<PHDStudent> get(String name);
	
	//search for a PHD student by name descending
	public List<PHDStudent> getDesc(String name);
	
	//save a PHD student with address and course
	public String save(@Autowired PHDStudent phdStudent, int aId, int cId);
	
	//save a PHD student without course
	public String save(@Autowired PHDStudent phdStudent, int aId);
	
	//add a course to a PHD student
	public String save(int id, int cId);
	
	//update a PHD student
	public String update(@Autowired PHDStudent pHDStudent);
	
	//delete a PHD student
	public String delete(int id);
	
	//drop a course from student's courses
	public String drop(int id, int cId);
	
	//delete all bachelor students
	public String deleteAll();
}
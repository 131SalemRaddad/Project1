package com.project.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.demo.model.MasterStudent;

public interface MasterStudentService {
	//get all master students
	public List<MasterStudent> getMasterStudents();
	
	//get master student by name in pages
	public Page<MasterStudent> get(String name, Pageable pageable);
	
	//get all master students in pages
	public List<MasterStudent> getAll(Pageable pageable);
	
	//search for a master student by name
	public List<MasterStudent> get(String name);
	
	//search for a master student by name descending
	public List<MasterStudent> getDesc(String name);
	
	//search for a master student by id
	public MasterStudent get(int id);
	
	//save a master student with address and course
	public String save(@Autowired MasterStudent masterStudent, int aId, int cId);
	
	//save a master student without course
	public String save(@Autowired MasterStudent masterStudent, int aId);
	
	//add a course to a master student
	public String save(int id, int cId);
	
	//update a master student
	public String update(@Autowired MasterStudent masterStudent);
	
	//delete a master student
	public String delete(int id);
	
	//drop a course from student's courses
	public String drop(int id, int cId);
	
	//delete all bachelor students
	public String deleteAll();
}
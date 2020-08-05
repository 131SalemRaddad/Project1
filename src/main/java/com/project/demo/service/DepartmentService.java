package com.project.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.demo.model.Department;

public interface DepartmentService {
	//get all departments
	public List<Department> getDepartments();
	
	//get department by name in pages
	public Page<Department> get(String name, Pageable pageable);
	
	//get all departments in pages
	public List<Department> getAll(Pageable pageable);
	
	//search for a department by id
	public Department get(int id);
	
	//search for a department by name
	public List<Department> get(String name);
	
	//search for a department by name descending
	public List<Department> getDesc(String name);
	
	//add a new department
	public String save(@Autowired Department department);
	
	//update a department
	public String update(@Autowired Department department);
	
	//delete a department
	public String delete(int id);
}
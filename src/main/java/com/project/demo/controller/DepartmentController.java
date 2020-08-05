package com.project.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.model.Department;
import com.project.demo.service.DepartmentServiceImpl;

@RestController
@RequestMapping("/department")
public class DepartmentController {
	@Autowired
	private DepartmentServiceImpl departmentServiceBean;
	
	@GetMapping(value="/all")
	public List<Department> getAll(){
		return departmentServiceBean.getDepartments();
	}
	
	@GetMapping(value = "/listPageable/{page}")
	public Page<Department> departmentPageable(@RequestBody String name, @PathVariable(value = "page")int page) {
		return departmentServiceBean.get(name, PageRequest.of(page, 2, Direction.ASC, "id"));
	}
	
	@GetMapping(value = "/allPages/{size}/{page}")
	public List<Department> allDepartmentsPageable(@PathVariable(value="size")int size, @PathVariable(value="page")int page){
		return departmentServiceBean.getAll(PageRequest.of(page, size, Direction.ASC, "id"));
	}
	
	@GetMapping(value="/get")
	public Department get(@RequestBody int id) {
		return departmentServiceBean.get(id);
	}
	
	@GetMapping(value = "/get/name")
	public List<Department> findByName(@RequestBody String name){
		return departmentServiceBean.get(name);
	}
	
	@GetMapping(value = "/get/name/desc")
	public List<Department> findByNameDesc(@RequestBody String name){
		return departmentServiceBean.getDesc(name);
	}
	
	@DeleteMapping(value="/delete")
    public String delete(@RequestBody int id) {
		return departmentServiceBean.delete(id);
    }
	
	@PostMapping(value="/add")
	public String add(@RequestBody Department department) {
		return departmentServiceBean.save(department);
	}
	
	@PutMapping(value="/update")
	public String update(@RequestBody Department department) {
		return departmentServiceBean.update(department);
	}
}
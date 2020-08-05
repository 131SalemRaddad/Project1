package com.project.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.demo.exception.APIException;
import com.project.demo.model.Department;
import com.project.demo.model.Instructor;
import com.project.demo.repository.DepartmentRepo;

@Service
public class DepartmentServiceImpl implements DepartmentService{
	@Autowired
	private DepartmentRepo rep;
	
	@Autowired
	private InstructorServiceImpl instructorBean;
	
	@Override
	public List<Department> getDepartments() {
		List<Department> departments = rep.findAll();
		if(departments.isEmpty())
			throw new APIException("The list of departments is empty");
		return departments;
	}
	
	public Page<Department> get(String name, Pageable pageable){
		Page<Department> page = rep.findByName(name, pageable);
		
		if(page.isEmpty())
			throw new APIException("The list of departments is empty");
		
		return page;
	}
	
	public List<Department> getAll(Pageable pageable){
		Page<Department> page = rep.findAll(pageable);
		
		if(page.isEmpty())
			throw new APIException("The list of departments is empty");
		
		return page.toList();
	}
	
	@Override
	public List<Department> get(String name){
		List<Department> departments = rep.findByName(name);
		
		if(departments.isEmpty())
			throw new APIException("The list of departments is empty");
		
		return departments;
	}
	
	@Override
	public List<Department> getDesc(String name){
		List<Department> departments = rep.findByNameDesc(name);
		
		if(departments.isEmpty())
			throw new APIException("The list of departments is empty");
		
		return departments;
	}

	@Override
	public Department get(int id) {
		Optional<Department> opt = rep.findById(id);
		if(opt.isEmpty())
			throw new APIException("Department not found");
		return opt.get();
	}

	@Override
	public String save(@Autowired Department department) {
		if(!rep.findById(department.getId()).isEmpty())
			throw new APIException("There is another department that has the same ID\nIf you want to update it go for PUT API");
		rep.save(department);
		return "The data added successfully";
	}

	@Override
	public String update(@Autowired Department department) {
		Department prevDepartment = rep.findById(department.getId()).get();
		if(prevDepartment == null)
			throw new APIException("Department not found");
		BeanUtils.copyProperties(department, prevDepartment);
		rep.save(department);
		return "Done successfully";
	}

	@Override
	public String delete(int id) {
		Department department = rep.findById(id).get();
		
		if(department == null)
			throw new APIException("Department not found");
		
		List<Instructor> instructors = department.getInstructors();
		
		for(int i=0;i<instructors.size();i++)
			instructorBean.delete(instructors.get(i).getId());
		
		rep.deleteById(id);
		return "The data has been deleted";
	}
}
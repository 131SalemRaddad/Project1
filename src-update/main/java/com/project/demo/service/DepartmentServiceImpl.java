package com.project.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
		try {
			List<Department> departments = rep.findAll();
			if(departments.isEmpty())
				throw new APIException("The list of departments is empty");
			return departments;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	public Page<Department> get(String name, Pageable pageable){
		try {
			Page<Department> page = rep.findByName(name, pageable);
			
			if(page.isEmpty())
				throw new APIException("The list of departments is empty");
			
			return page;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	public List<Department> getAll(Pageable pageable){
		try {
			Page<Department> page = rep.findAll(pageable);
			
			if(page.isEmpty())
				throw new APIException("The list of departments is empty");
			
			return page.toList();
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	@Override
	public List<Department> get(String name){
		try {
			List<Department> departments = rep.findByName(name);
			
			if(departments.isEmpty())
				throw new APIException("The list of departments is empty");
			
			return departments;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	@Override
	public List<Department> getDesc(String name){
		try {
			List<Department> departments = rep.findByNameDesc(name);
			
			if(departments.isEmpty())
				throw new APIException("The list of departments is empty");
			
			return departments;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public Department get(int id) {
		try {
			Optional<Department> opt = rep.findById(id);
			if(opt.isEmpty())
				throw new APIException("Department not found");
			return opt.get();
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public String save(@Autowired Department department) {
		try {
			if(!rep.findById(department.getId()).isEmpty())
				throw new APIException("There is another department that has the same ID\nIf you want to update it go for PUT API");
			rep.save(department);
			return "The data added successfully";
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public String update(@Autowired Department department) {
		try {
			Department prevDepartment = rep.findById(department.getId()).get();
			if(prevDepartment == null)
				throw new APIException("Department not found");
			
			Set<String> notIncluded = NoNullProperties.getNullPropertyNames(department);
			notIncluded.add("instructors");
			notIncluded.add("courses");
			String[] result = new String[notIncluded.size()];
			BeanUtils.copyProperties(department, prevDepartment, notIncluded.toArray(result));
			
			rep.save(prevDepartment);
			return "Done successfully";
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public String delete(int id) {
		try {
			Department department = rep.findById(id).get();
			
			if(department == null)
				throw new APIException("Department not found");
			
			List<Instructor> instructors = department.getInstructors();
			
			for(int i=0;i<instructors.size();i++)
				instructorBean.delete(instructors.get(i).getId());
			
			rep.deleteById(id);
			return "The data has been deleted";
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	@Override
	public String deleteAll() {
		try {
			List<Department> list = rep.findAll();
			if(list.isEmpty())
				throw new APIException("The list is empty");
			
			for(int i=0;i<list.size();i++)
				rep.deleteById(list.get(i).getId());
			
			return "The data has been deleted";
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
}
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
import com.project.demo.model.Address;
import com.project.demo.model.Courses;
import com.project.demo.model.Department;
import com.project.demo.model.Instructor;
import com.project.demo.repository.AddressRepo;
import com.project.demo.repository.DepartmentRepo;
import com.project.demo.repository.InstructorRepo;

@Service
public class InstructorServiceImpl implements InstructorService{
	@Autowired
	InstructorRepo rep;
	
	@Autowired
	private AddressRepo addressRepo;
	
	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Autowired
	private CoursesServiceImpl coursesServiceBean;
	
	@Override
	public List<Instructor> getInstructors() {
		try {
			List<Instructor> instructors = rep.findAll();
			if(instructors.isEmpty())
				throw new APIException("The list of instructors is empty");
			return instructors;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	public Page<Instructor> get(String name, Pageable pageable){
		try {
			Page<Instructor> page = rep.findByName(name, pageable);
			
			if(page.isEmpty())
				throw new APIException("The list of instructors is empty");
			
			return page;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	public List<Instructor> getAll(Pageable pageable){
		try {
			Page<Instructor> page = rep.findAll(pageable);
			
			if(page.isEmpty())
				throw new APIException("The list of instructors is empty");
			
			return page.toList();
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	@Override
	public List<Instructor> get(String name){
		try {
			List<Instructor> instructors = rep.findByName(name);
			
			if(instructors.isEmpty())
				throw new APIException("The list of instructors is empty");
			
			return instructors;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	@Override
	public List<Instructor> getDesc(String name){
		try {
			List<Instructor> instructors = rep.findByNameDesc(name);
			
			if(instructors.isEmpty())
				throw new APIException("The list of instructors is empty");
			
			return instructors;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public Instructor get(int id) {
		try {
			Optional<Instructor> opt = rep.findById(id);
			if(opt.isEmpty())
				throw new APIException("Instructor not found");
			return opt.get();
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public String save(@Autowired Instructor instructor, int aId, int dId) {
		try {
			if(!rep.findById(instructor.getId()).isEmpty())
				throw new APIException("There is another instructor that has the same ID\nIf you want to update it go for PUT API");
			
			Optional<Address> optAddress = addressRepo.findById(aId);
			Optional<Department> optDepartment = departmentRepo.findById(dId);
			if(optAddress.isEmpty())
				throw new APIException("Address not found");
			if(optDepartment.isEmpty())
				throw new APIException("Department not found");
			optAddress.get().addInstructors(instructor);
			optDepartment.get().addInstructors(instructor);
			instructor.setAddress(optAddress.get());
			instructor.setDepartment(optDepartment.get());
			
			rep.save(instructor);
			return "The data added successfully";
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public String update(@Autowired Instructor instructor) {
		try {
			Instructor prevInstructor = rep.findById(instructor.getId()).get();
			if(prevInstructor == null)
				throw new APIException("Instructor not found");
			
			Set<String> notIncluded = NoNullProperties.getNullPropertyNames(instructor);
			notIncluded.add("address");
			notIncluded.add("department");
			notIncluded.add("courses");
			String[] result = new String[notIncluded.size()];
			BeanUtils.copyProperties(instructor, prevInstructor, notIncluded.toArray(result));
			
			rep.save(prevInstructor);
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
			Instructor instructor = rep.findById(id).get();
			if(instructor == null)
				throw new APIException("Instructor not found");
			
			List<Courses> courses = instructor.getCourses();
			
			for(int i=0;i<courses.size();i++)
				coursesServiceBean.delete(courses.get(i).getId());
			
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
			List<Instructor> list = rep.findAll();
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
package com.project.demo.service;

import java.util.List;
import java.util.Optional;

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
		List<Instructor> instructors = rep.findAll();
		if(instructors.isEmpty())
			throw new APIException("The list of instructors is empty");
		return instructors;
	}
	
	public Page<Instructor> get(String name, Pageable pageable){
		Page<Instructor> page = rep.findByName(name, pageable);
		
		if(page.isEmpty())
			throw new APIException("The list of instructors is empty");
		
		return page;
	}
	
	public List<Instructor> getAll(Pageable pageable){
		Page<Instructor> page = rep.findAll(pageable);
		
		if(page.isEmpty())
			throw new APIException("The list of instructors is empty");
		
		return page.toList();
	}
	
	@Override
	public List<Instructor> get(String name){
		List<Instructor> instructors = rep.findByName(name);
		
		if(instructors.isEmpty())
			throw new APIException("The list of instructors is empty");
		
		return instructors;
	}
	
	@Override
	public List<Instructor> getDesc(String name){
		List<Instructor> instructors = rep.findByNameDesc(name);
		
		if(instructors.isEmpty())
			throw new APIException("The list of instructors is empty");
		
		return instructors;
	}

	@Override
	public Instructor get(int id) {
		Optional<Instructor> opt = rep.findById(id);
		if(opt.isEmpty())
			throw new APIException("Instructor not found");
		return opt.get();
	}

	@Override
	public String save(@Autowired Instructor instructor, int aId, int dId) {
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
	}

	@Override
	public String update(@Autowired Instructor instructor) {
		Instructor prevInstructor = rep.findById(instructor.getId()).get();
		if(prevInstructor == null)
			throw new APIException("Instructor not found");
		BeanUtils.copyProperties(instructor, prevInstructor);
		rep.save(instructor);
		return "Done successfully";
	}

	@Override
	public String delete(int id) {
		Instructor instructor = rep.findById(id).get();
		if(instructor == null)
			throw new APIException("Instructor not found");
		
		List<Courses> courses = instructor.getCourses();
		
		for(int i=0;i<courses.size();i++)
			coursesServiceBean.delete(courses.get(i).getId());
		
		rep.deleteById(id);
		return "The data has been deleted";
	}
}
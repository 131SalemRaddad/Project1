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
import com.project.demo.model.Courses;
import com.project.demo.model.Department;
import com.project.demo.model.Instructor;
import com.project.demo.repository.CoursesRepo;
import com.project.demo.repository.DepartmentRepo;
import com.project.demo.repository.InstructorRepo;

@Service
public class CoursesServiceImpl implements CoursesService{
	@Autowired
	private CoursesRepo rep;
	
	@Autowired
	private InstructorRepo instructorRepo;
	
	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Override
	public List<Courses> getCourses() {
		try {
			List<Courses> courses = rep.findAll();
			if(courses.isEmpty())
				throw new APIException("The list of courses is empty");
			return courses;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	public Page<Courses> getCoursesByName(String name, Pageable pageable){
		try {
			Page<Courses> page = rep.findByName(name, pageable);
			
			if(page.isEmpty())
				throw new APIException("The list of courses is empty");
			
			return page;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	public List<Courses> getAll(Pageable pageable){
		try {
			Page<Courses> page = rep.findAll(pageable);
			
			if(page.isEmpty())
				throw new APIException("The list of courses is empty");
			
			return page.toList();
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	@Override
	public List<Courses> get(String name){
		try {
			List<Courses> courses = rep.findByName(name);
			
			if(courses.isEmpty())
				throw new APIException("The list of courses is empty");
			
			return courses;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	@Override
	public List<Courses> getDesc(String name){
		try {
			List<Courses> courses = rep.findByNameDesc(name);
			
			if(courses.isEmpty())
				throw new APIException("The list of courses is empty");
			
			return courses;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public Courses get(int id) {
		try {
			Optional<Courses> opt = rep.findById(id);
			if(opt.isEmpty())
				throw new APIException("Course not found");
			return opt.get();
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public String save(Courses course, int iId, int dId) {
		try {
			if(!rep.findById(course.getId()).isEmpty())
				throw new APIException("There is another course that has the same ID\nIf you want to update it go for PUT API");
			
			Optional<Instructor> optInstructor = instructorRepo.findById(iId);
			Optional<Department> optDepartment = departmentRepo.findById(iId);
			
			if(optInstructor.isEmpty())
				throw new APIException("Instructor not found");
			
			if(optDepartment.isEmpty())
				throw new APIException("Department not found");
			
			optInstructor.get().addCourses(course);
			optDepartment.get().addCourses(course);
			course.setInstructor(optInstructor.get());
			course.setDepartment(optDepartment.get());
			
			rep.save(course);
			return "The data added successfully";
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public String update(@Autowired Courses course) {
		try {
			Courses prevCourse = rep.findById(course.getId()).get();
			if(prevCourse == null)
				throw new APIException("Course not found");
			
			Set<String> notIncluded = NoNullProperties.getNullPropertyNames(course);
			notIncluded.add("bachelorStudents");
			notIncluded.add("masterStudents");
			notIncluded.add("phdStudents");
			notIncluded.add("instructor");
			String[] result = new String[notIncluded.size()];
			BeanUtils.copyProperties(course, prevCourse, notIncluded.toArray(result));
			
			rep.save(prevCourse);
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
			if(rep.findById(id).isEmpty())
				throw new APIException("Course not found");
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
			if(rep.findAll().isEmpty())
				throw new APIException("The list is empty");
			
			rep.deleteAll();
			
			return "The data has been deleted";
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public String addRawCourse(Courses course) {
		if(!rep.findById(course.getId()).isEmpty())
			throw new APIException("There is another course that has the same ID");
		
		rep.save(course);
		return "The data saved successfully";
	}
}
package com.project.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.demo.exception.APIException;
import com.project.demo.model.Courses;
import com.project.demo.repository.CoursesRepo;

@Service
public class CoursesServiceImpl implements CoursesService{
	@Autowired
	private CoursesRepo rep;
	
	@Override
	public List<Courses> getCourses() {
		List<Courses> courses = rep.findAll();
		if(courses.isEmpty())
			throw new APIException("The list of courses is empty");
		return courses;
	}
	
	public Page<Courses> get(String name, Pageable pageable){
		Page<Courses> page = rep.findByName(name, pageable);
		
		if(page.isEmpty())
			throw new APIException("The list of courses is empty");
		
		return page;
	}
	
	public List<Courses> getAll(Pageable pageable){
		Page<Courses> page = rep.findAll(pageable);
		
		if(page.isEmpty())
			throw new APIException("The list of courses is empty");
		
		return page.toList();
	}
	
	@Override
	public List<Courses> get(String name){
		List<Courses> courses = rep.findByName(name);
		
		if(courses.isEmpty())
			throw new APIException("The list of courses is empty");
		
		return courses;
	}
	
	@Override
	public List<Courses> getDesc(String name){
		List<Courses> courses = rep.findByNameDesc(name);
		
		if(courses.isEmpty())
			throw new APIException("The list of courses is empty");
		
		return courses;
	}

	@Override
	public Courses get(int id) {
		Optional<Courses> opt = rep.findById(id);
		if(opt.isEmpty())
			throw new APIException("Course not found");
		return opt.get();
	}

	@Override
	public String save(@Autowired Courses course, int iId, int dId) {
		if(!rep.findById(course.getId()).isEmpty())
			throw new APIException("There is another course that has the same ID\nIf you want to update it go for PUT API");
		
		rep.save(course);
		return "The data added successfully";
	}

	@Override
	public String update(@Autowired Courses course) {
		Courses prevCourse = rep.findById(course.getId()).get();
		if(prevCourse == null)
			throw new APIException("Course not found");
		BeanUtils.copyProperties(course, prevCourse);
		rep.save(course);
		return "Done successfully";
	}

	@Override
	public String delete(int id) {
		if(rep.findById(id).isEmpty())
			throw new APIException("Course not found");
		rep.deleteById(id);
		return "The data has been deleted";
	}
}
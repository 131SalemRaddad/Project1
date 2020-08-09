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

import com.project.demo.model.Courses;
import com.project.demo.service.CoursesServiceImpl;

@RestController
@RequestMapping("/course")
public class CoursesController {
	@Autowired
	CoursesServiceImpl coursesServiceBean;
	
	@GetMapping(value="/all")
	public List<Courses> getAll(){
		return coursesServiceBean.getCourses();
	}
	
	@GetMapping(value = "/listPageable/{name}/{page}")
	public Page<Courses> coursePageable(@PathVariable(value = "name")String name, @PathVariable(value = "page")int page) {
		return coursesServiceBean.getCoursesByName(name, PageRequest.of(page, 2, Direction.ASC, "id"));
	}
	
	@GetMapping(value = "/allPages/{size}/{page}")
	public List<Courses> allCoursesPageable(@PathVariable(value="size")int size, @PathVariable(value="page")int page){
		return coursesServiceBean.getAll(PageRequest.of(page, size, Direction.ASC, "id"));
	}
	
	@GetMapping(value="/get/{id}")
	public Courses get(@PathVariable(value = "id")int id) {
		return coursesServiceBean.get(id);
	}
	
	@GetMapping(value = "/get/name/{name}")
	public List<Courses> findByName(@PathVariable(value = "name") String name){
		return coursesServiceBean.get(name);
	}
	
	@GetMapping(value = "/get/desc/{name}")
	public List<Courses> findByNameDesc(@PathVariable(value = "name") String name){
		return coursesServiceBean.getDesc(name);
	}
	
	@DeleteMapping(value="/delete/{id}")
    public String delete(@PathVariable(value = "id")int id) {
		return coursesServiceBean.delete(id);
    }
	
	@PostMapping(value="/add/{iId}/{dId}")
	public String add(@RequestBody Courses course, @PathVariable(value = "iId")int iId, @PathVariable(value = "dId")int dId) {
		return coursesServiceBean.save(course, iId, dId);
	}
	
	@PutMapping(value="/update")
	public String update(@RequestBody Courses course) {
		return coursesServiceBean.update(course);
	}
	
	@DeleteMapping(value="/deleteAll")
	public String deleteAll() {
		return coursesServiceBean.deleteAll();
	}
	
	@PostMapping(value = "/addRaw")
	public String addRawCourse(@RequestBody Courses course) {
		return coursesServiceBean.addRawCourse(course);
	}
}
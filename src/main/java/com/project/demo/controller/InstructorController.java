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

import com.project.demo.model.Instructor;
import com.project.demo.service.InstructorServiceImpl;

@RestController
@RequestMapping("/instructor")
public class InstructorController {
	@Autowired
	InstructorServiceImpl instructorServiceBean;
	
	@GetMapping(value="/all")
	public List<Instructor> getAll(){
		return instructorServiceBean.getInstructors();
	}
	
	@GetMapping(value = "/listPageable/{name}/{page}")
	public Page<Instructor> instructorPageable(@PathVariable(value = "name")String name, @PathVariable(value = "page")int page) {
		return instructorServiceBean.get(name, PageRequest.of(page, 2, Direction.ASC, "id"));
	}
	
	@GetMapping(value = "/allPages/{size}/{page}")
	public List<Instructor> allInstructorsPageable(@PathVariable(value="size")int size, @PathVariable(value="page")int page){
		return instructorServiceBean.getAll(PageRequest.of(page, size, Direction.ASC, "id"));
	}
	
	@GetMapping(value="/get/{id}")
	public Instructor get(@PathVariable(value = "id")int id) {
		return instructorServiceBean.get(id);
	}
	
	@GetMapping(value = "/get/name/{name}")
	public List<Instructor> findByName(@PathVariable(value = "name") String name){
		return instructorServiceBean.get(name);
	}
	
	@GetMapping(value = "/get/desc/{name}")
	public List<Instructor> findByNameDesc(@PathVariable(value = "name")String name){
		return instructorServiceBean.getDesc(name);
	}
	
	@DeleteMapping(value="/delete/{id}")
    public String delete(@PathVariable(value = "id")int id) {
		return instructorServiceBean.delete(id);
    }
	
	@PostMapping(value="/add/{aid}/{did}")
	public String add(@RequestBody Instructor instructor, @PathVariable(value = "aid")int aId, @PathVariable(value = "did")int dId) {
		return instructorServiceBean.save(instructor, aId, dId);
	}
	
	@PutMapping(value="/update")
	public String update(@RequestBody Instructor instructor) {
		return instructorServiceBean.update(instructor);
	}
	
	@DeleteMapping(value="/deleteAll")
	public String deleteAll() {
		return instructorServiceBean.deleteAll();
	}
}
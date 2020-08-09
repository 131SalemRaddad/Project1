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

import com.project.demo.model.BachelorStudent;
import com.project.demo.model.Courses;
import com.project.demo.service.BachelorStudentServiceImpl;

@RestController
@RequestMapping("/bachelorStudent")
public class BachelorStudentController {
	
	@Autowired
	BachelorStudentServiceImpl bachelorStudentServiceBean;
	
	@GetMapping(value="/all")
	public List<BachelorStudent> getAll(){
		return bachelorStudentServiceBean.getBachelorStudents();
	}
	
	@GetMapping(value = "/listPageable/{name}/{page}")
	public Page<BachelorStudent> bachelorStudentPageable(@PathVariable(value = "name")String name, @PathVariable(value = "page")int page) {
		return bachelorStudentServiceBean.get(name, PageRequest.of(page, 2, Direction.ASC, "id"));
	}
	
	@GetMapping(value = "/allPages/{size}/{page}")
	public List<BachelorStudent> allBachelorStudentsPageable(@PathVariable(value="size")int size, @PathVariable(value="page")int page){
		return bachelorStudentServiceBean.getAll(PageRequest.of(page, size, Direction.ASC, "id"));
	}
	
	@GetMapping(value = "/get/name/{name}")
	public List<BachelorStudent> findByName(@PathVariable(value = "name")String name){
		return bachelorStudentServiceBean.get(name);
	}
	
	@GetMapping(value = "/get/desc/{name}")
	public List<BachelorStudent> findByNameDesc(@PathVariable(value = "name") String name){
		return bachelorStudentServiceBean.getDesc(name);
	}
	
	@GetMapping(value="/get/{id}")
	public BachelorStudent get(@PathVariable(value = "id")int id) {
		BachelorStudent s = bachelorStudentServiceBean.get(id);
		return s;
	}
	
	@DeleteMapping(value="/delete/{id}")
    public String delete(@PathVariable(value = "id")int id) {
		return bachelorStudentServiceBean.delete(id);
    }
	
	@DeleteMapping(value="/drop/course/{id}/{cId}")
    public String delete(@PathVariable(value = "id")int id, @PathVariable(value = "id")int cId) {
		return bachelorStudentServiceBean.drop(id, cId);
    }
	
	@PostMapping(value="/add/address/{aid}/course/{cid}")
	public String add(@RequestBody BachelorStudent bachelorStudent, @PathVariable(value = "aid")int aid, @PathVariable(value = "cid")int cid) {
		return bachelorStudentServiceBean.save(bachelorStudent, aid, cid);
	}
	
	@PostMapping(value="/add/address/{aid}")
	public String add(@RequestBody BachelorStudent bachelorStudent, @PathVariable(value = "aid")int aid) {
		return bachelorStudentServiceBean.save(bachelorStudent, aid);
	}
	
	@PostMapping(value="/add/{id}/course/{cid}")
	public String add(@PathVariable(value = "id")int id, @PathVariable(value = "cid")int cid) {
		return bachelorStudentServiceBean.save(id, cid);
	}
	
	@PutMapping(value="/update")
	public String update(@RequestBody BachelorStudent bachelorStudent) {		
		return bachelorStudentServiceBean.update(bachelorStudent);
	}
	
	@DeleteMapping(value="/deleteAll")
	public String deleteAll() {
		return bachelorStudentServiceBean.deleteAll();
	}
	
	@GetMapping(value = "/{cName}")
	public List<Courses> getCoursesByName(@PathVariable(value = "cName")String cName){
		return bachelorStudentServiceBean.getCoursesByName(cName);
	}
}
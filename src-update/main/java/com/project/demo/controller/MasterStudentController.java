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

import com.project.demo.model.MasterStudent;
import com.project.demo.service.MasterStudentServiceImpl;

@RestController
@RequestMapping("/masterStudent")
public class MasterStudentController {
	@Autowired
	MasterStudentServiceImpl masterStudentServiceBean;
	
	@GetMapping(value="/all")
	public List<MasterStudent> getAll(){
		return masterStudentServiceBean.getMasterStudents();
	}
	
	@GetMapping(value = "/listPageable/{name}/{page}")
	public Page<MasterStudent> masterStudentPageable(@PathVariable(value = "name")String name, @PathVariable(value = "page")int page) {
		return masterStudentServiceBean.get(name, PageRequest.of(page, 2, Direction.ASC, "id"));
	}
	
	@GetMapping(value = "/allPages/{size}/{page}")
	public List<MasterStudent> allMasterStudentsPageable(@PathVariable(value="size")int size, @PathVariable(value="page")int page){
		return masterStudentServiceBean.getAll(PageRequest.of(page, size, Direction.ASC, "id"));
	}
	
	@GetMapping(value = "/get/name/{name}")
	public List<MasterStudent> findByName(@PathVariable(value = "name")String name){
		return masterStudentServiceBean.get(name);
	}
	
	@GetMapping(value = "/get/desc/{name}")
	public List<MasterStudent> findByNameDesc(@PathVariable(value = "name")String name){
		return masterStudentServiceBean.getDesc(name);
	}
	
	@GetMapping(value="/get/{id}")
	public MasterStudent get(@PathVariable(value = "id")int id) {
		return masterStudentServiceBean.get(id);
	}
	
	@DeleteMapping(value="/delete/{id}")
    public String delete(@PathVariable(value = "id")int id) {
		return masterStudentServiceBean.delete(id);
    }
	
	@DeleteMapping(value="/drop/course/{id}/{cId}")
    public String delete(@PathVariable(value = "id")int id, @PathVariable(value = "id")int cId) {
		return masterStudentServiceBean.drop(id, cId);
    }
	
	@PostMapping(value="/add/address/{aid}/course/{cid}")
	public String add(@RequestBody MasterStudent masterStudent, @PathVariable(value = "aid")int aid, @PathVariable(value = "cid")int cid) {
		return masterStudentServiceBean.save(masterStudent, aid, cid);
	}
	
	@PostMapping(value="/add/address/{aid}")
	public String add(@RequestBody MasterStudent masterStudent, @PathVariable(value = "aid")int aid) {
		return masterStudentServiceBean.save(masterStudent, aid);
	}
	
	@PostMapping(value="/add/{id}/course/{cid}")
	public String add(@PathVariable(value = "id")int id, @PathVariable(value = "cid")int cid) {
		return masterStudentServiceBean.save(id, cid);
	}
	
	@PutMapping(value="/update")
	public String update(@RequestBody MasterStudent masterStudent) {
		return masterStudentServiceBean.update(masterStudent);
	}
	
	@DeleteMapping(value="/deleteAll")
	public String deleteAll() {
		return masterStudentServiceBean.deleteAll();
	}
}
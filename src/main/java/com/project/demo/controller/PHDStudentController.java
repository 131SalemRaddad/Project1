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

import com.project.demo.model.PHDStudent;
import com.project.demo.service.PHDStudentServiceImpl;

@RestController
@RequestMapping("/phdStudent")
public class PHDStudentController {
	@Autowired
	PHDStudentServiceImpl pHDStudentServiceBean;
	
	@GetMapping(value="/all")
	public List<PHDStudent> getAll(){
		return pHDStudentServiceBean.getPHDStudents();
	}
	
	@GetMapping(value = "/listPageable/{page}")
	public Page<PHDStudent> phdStudentPageable(@RequestBody String name, @PathVariable(value = "page")int page) {
		return pHDStudentServiceBean.get(name, PageRequest.of(page, 2, Direction.ASC, "id"));
	}
	
	@GetMapping(value = "/allPages/{size}/{page}")
	public List<PHDStudent> allPHDStudentsPageable(@PathVariable(value="size")int size, @PathVariable(value="page")int page){
		return pHDStudentServiceBean.getAll(PageRequest.of(page, size, Direction.ASC, "id"));
	}
	
	@GetMapping(value = "/get/name")
	public List<PHDStudent> findByName(@RequestBody String name){
		return pHDStudentServiceBean.get(name);
	}
	
	@GetMapping(value = "/get/name/desc")
	public List<PHDStudent> findByNameDesc(@RequestBody String name){
		return pHDStudentServiceBean.getDesc(name);
	}
	
	@GetMapping(value="/get")
	public PHDStudent get(@RequestBody int id) {
		return pHDStudentServiceBean.get(id);
	}
	
	@DeleteMapping(value="/delete")
    public String delete(@RequestBody int id) {
		return pHDStudentServiceBean.delete(id);
    }
	
	@DeleteMapping(value="/drop/course/{cId}")
    public String delete(@RequestBody int id, @PathVariable(value = "id")int cId) {
		return pHDStudentServiceBean.drop(id, cId);
    }
	
	@PostMapping(value="/add/address/{aid}/course/{cid}")
	public String add(@RequestBody PHDStudent phdStudent, @PathVariable(value = "aid")int aid, @PathVariable(value = "cid")int cid) {
		return pHDStudentServiceBean.save(phdStudent, aid, cid);
	}
	
	@PostMapping(value="/add/address/{aid}")
	public String add(@RequestBody PHDStudent phdStudent, @PathVariable(value = "aid")int aid) {
		return pHDStudentServiceBean.save(phdStudent, aid);
	}
	
	@PostMapping(value="/add/{id}/course/{cid}")
	public String add(@PathVariable(value = "id")int id, @PathVariable(value = "cid")int cid) {
		return pHDStudentServiceBean.save(id, cid);
	}
	
	@PutMapping(value="/update")
	public String update(@RequestBody PHDStudent pHDStudent) {
		return pHDStudentServiceBean.update(pHDStudent);
	}
	
	@DeleteMapping(value="/deleteAll")
	public String deleteAll() {
		return pHDStudentServiceBean.deleteAll();
	}
}
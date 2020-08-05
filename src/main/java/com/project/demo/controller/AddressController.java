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

import com.project.demo.model.Address;
import com.project.demo.service.AddressServiceImpl;

@RestController
@RequestMapping("/address")
public class AddressController {
	@Autowired
	private AddressServiceImpl addressServiceBean;
	
	@GetMapping(value="/all")
	public List<Address> getAll(){
		return addressServiceBean.getAddresses();
	}
	
	@GetMapping(value = "/listPageable/{page}")
	public Page<Address> addressPageable(@RequestBody String name, @PathVariable(value = "page")int page) {
		return addressServiceBean.get(name, PageRequest.of(page, 2, Direction.ASC, "id"));
	}
	
	@GetMapping(value = "/allPages/{size}/{page}")
	public List<Address> allAddressesPageable(@PathVariable(value="size")int size, @PathVariable(value="page")int page){
		return addressServiceBean.getAll(PageRequest.of(page, size, Direction.ASC, "id"));
	}
	
	@GetMapping(value="/get")
	public Address get(@RequestBody int id) {
		return addressServiceBean.get(id);
	}
	
	@GetMapping(value = "/get/name")
	public List<Address> findByName(@RequestBody String name){
		return addressServiceBean.get(name);
	}
	
	@GetMapping(value = "/get/name/desc")
	public List<Address> findByNameDesc(@RequestBody String name){
		return addressServiceBean.getDesc(name);
	}
	
	@DeleteMapping(value="/delete")
    public String delete(@RequestBody int id) {
		return addressServiceBean.delete(id);
    }
	
	@PostMapping(value="/add")
	public String add(@RequestBody Address address) {
		return addressServiceBean.save(address);
	}
	
	@PutMapping(value="/update")
	public String update(@RequestBody Address address) {
		return addressServiceBean.update(address);
	}
	
//	@DeleteMapping(value="/deleteAll")
//	public String deleteAll() {
//		return addressServiceBean.deleteAll();
//	}
}
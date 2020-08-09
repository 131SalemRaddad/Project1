package com.project.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.demo.model.Address;

public interface AddressService {
	//get all addresses
	public List<Address> getAddresses();
	
	//get address by name in pages
	public Page<Address> get(String name, Pageable pageable);
	
	//get all addresses in pages
	public List<Address> getAll(Pageable pageable);
	
	//search for an address by id
	public Address get(int id);
	
	//search for an address by name
	public List<Address> get(String name);
	
	//search for an address by name descending
	public List<Address> getDesc(String name);
	
	//add a new address
	public String save(@Autowired Address address);
	
	//update an address
	public String update(@Autowired Address address);
	
	//delete an address
	public String delete(int id);
	
//	delete all addresses
	public String deleteAll();
}
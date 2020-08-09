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
import com.project.demo.model.Address;
import com.project.demo.model.BachelorStudent;
import com.project.demo.model.Instructor;
import com.project.demo.model.MasterStudent;
import com.project.demo.model.PHDStudent;
import com.project.demo.repository.AddressRepo;

@Service
public class AddressServiceImpl implements AddressService{
	@Autowired
	private AddressRepo rep;
	
	@Autowired
	private BachelorStudentServiceImpl bachStudBean;
	
	@Autowired
	private MasterStudentServiceImpl mastStudBean;
	
	@Autowired
	private PHDStudentServiceImpl phdStudBean;
	
	@Autowired
	private InstructorServiceImpl instructorBean;

	@Override
	public List<Address> getAddresses() {
		try {
			List<Address> addresses = rep.findAll();
			if(addresses.isEmpty())
				throw new APIException("The list of addresses is empty");
			return addresses;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	public Page<Address> get(String name, Pageable pageable){
		try {
			Page<Address> page = rep.findByName(name, pageable);
			
			if(page.isEmpty())
				throw new APIException("The list of addresses is empty");
			
			return page;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	public List<Address> getAll(Pageable pageable){
		try {
			Page<Address> page = rep.findAll(pageable);
			
			if(page.isEmpty())
				throw new APIException("The list of addresses is empty");
			
			return page.toList();
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	@Override
	public List<Address> get(String name){
		try {
			List<Address> addresses = rep.findByName(name);
			
			if(addresses.isEmpty())
				throw new APIException("The list of addresses is empty");
			
			return addresses;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	@Override
	public List<Address> getDesc(String name){
		try {
			List<Address> addresses = rep.findByNameDesc(name);
			
			if(addresses.isEmpty())
				throw new APIException("The list of addresses is empty");
			
			return addresses;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public Address get(int id) {
		try {
			Optional<Address> opt = rep.findById(id);
			if(opt.isEmpty())
				throw new APIException("Address not found");
			return opt.get();
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public String save(@Autowired Address address) {
		try {
			if(!rep.findById(address.getId()).isEmpty())
				throw new APIException("There is another address that has the same ID"
					+ "\nIf you want to update it go for PUT API");
			rep.save(address);
			return "The data added successfully";
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public String update(@Autowired Address address) {
		try {
			Address prevAddress = rep.findById(address.getId()).get();
			if(prevAddress == null)
				throw new APIException("Address not found");
			
			Set<String> notIncluded = NoNullProperties.getNullPropertyNames(address);
			notIncluded.add("bachelorStudents");
			notIncluded.add("masterStudents");
			notIncluded.add("phdStudents");
			notIncluded.add("instructors");
			String[] result = new String[notIncluded.size()];
			BeanUtils.copyProperties(address, prevAddress, notIncluded.toArray(result));
			
			rep.save(prevAddress);
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
			Address address = rep.findById(id).get();
			if(address == null)
				throw new APIException("Address not found");
			
			List<BachelorStudent> bachelorStudents = address.getBachelorStudents();
			List<MasterStudent> masterStudents = address.getMasterStudents();
			List<PHDStudent> phdStudents = address.getPhdStudents();
			List<Instructor> instructors = address.getInstructors();
			
			for(int i=0;i<bachelorStudents.size();i++)
				bachStudBean.delete(bachelorStudents.get(i).getId());
			
			for(int i=0;i<masterStudents.size();i++)
				mastStudBean.delete(masterStudents.get(i).getId());
			
			for(int i=0;i<phdStudents.size();i++)
				phdStudBean.delete(phdStudents.get(i).getId());
			
			for(int i=0;i<instructors.size();i++)
				instructorBean.delete(instructors.get(i).getId());
			
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
			List<Address> list = rep.findAll();
			if(list.isEmpty())
				throw new APIException("The list is empty");
			
			for(int i=0;i<list.size();i++)
				rep.deleteById(list.get(i).getId());
			
			return "The data has been deleted";
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
}
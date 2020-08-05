package com.project.demo.service;

import java.util.List;
import java.util.Optional;

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
		List<Address> addresses = rep.findAll();
		if(addresses.isEmpty())
			throw new APIException("The list of addresses is empty");
		return addresses;
	}
	
	public Page<Address> get(String name, Pageable pageable){
		Page<Address> page = rep.findByName(name, pageable);
		
		if(page.isEmpty())
			throw new APIException("The list of addresses is empty");
		
		return page;
	}
	
	public List<Address> getAll(Pageable pageable){
		Page<Address> page = rep.findAll(pageable);
		
		if(page.isEmpty())
			throw new APIException("The list of addresses is empty");
		
		return page.toList();
	}
	
	@Override
	public List<Address> get(String name){
		List<Address> addresses = rep.findByName(name);
		
		if(addresses.isEmpty())
			throw new APIException("The list of addresses is empty");
		
		return addresses;
	}
	
	@Override
	public List<Address> getDesc(String name){
		List<Address> addresses = rep.findByNameDesc(name);
		
		if(addresses.isEmpty())
			throw new APIException("The list of addresses is empty");
		
		return addresses;
	}

	@Override
	public Address get(int id) {
		Optional<Address> opt = rep.findById(id);
		if(opt.isEmpty())
			throw new APIException("Address not found");
		return opt.get();
	}

	@Override
	public String save(@Autowired Address address) {
		if(!rep.findById(address.getId()).isEmpty())
			throw new APIException("There is another address that has the same ID"
					+ "\nIf you want to update it go for PUT API");
		rep.save(address);
		return "The data added successfully";
	}

	@Override
	public String update(@Autowired Address address) {
		Address prevAddress = rep.findById(address.getId()).get();
		if(prevAddress == null)
			throw new APIException("Address not found");
		BeanUtils.copyProperties(address, prevAddress);
		rep.save(address);
		return "Done successfully";
	}

	@Override
	public String delete(int id) {
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
		
	}

//	@Override
//	public String deleteAll() {
//		if(rep.findAll().isEmpty())
//			throw new APIException("The list is empty");
//		rep.deleteAll();
//		return "The data has been deleted";
//	}
}
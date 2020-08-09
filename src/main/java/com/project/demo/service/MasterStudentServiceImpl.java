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
import com.project.demo.model.Courses;
import com.project.demo.model.MasterStudent;
import com.project.demo.repository.AddressRepo;
import com.project.demo.repository.CoursesRepo;
import com.project.demo.repository.MasterStudentRepo;

@Service
public class MasterStudentServiceImpl implements MasterStudentService {
	@Autowired
	MasterStudentRepo rep;
	
	@Autowired
	private AddressRepo addressRepo;
	
	@Autowired
	private CoursesRepo courseRepo;
	
	@Override
	public List<MasterStudent> getMasterStudents() {
		try {
			List<MasterStudent> masterStudents = rep.findAll();
			if(masterStudents.isEmpty())
				throw new APIException("The list of master students is empty");
			return masterStudents;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	public Page<MasterStudent> get(String name, Pageable pageable){
		try {
			Page<MasterStudent> page = rep.findByName(name, pageable);
			
			if(page.isEmpty())
				throw new APIException("The list of master students is empty");
			
			return page;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	public List<MasterStudent> getAll(Pageable pageable){
		try {
			Page<MasterStudent> page = rep.findAll(pageable);
			
			if(page.isEmpty())
				throw new APIException("The list of master students is empty");
			
			return page.toList();
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public List<MasterStudent> get(String name){
		try {
			List<MasterStudent> masterStudents = rep.findByName(name);
			
			if(masterStudents.isEmpty())
				throw new APIException("The list of master students is empty");
			
			return masterStudents;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	@Override
	public List<MasterStudent> getDesc(String name){
		try {
			List<MasterStudent> masterStudents = rep.findByNameDesc(name);
			
			if(masterStudents.isEmpty())
				throw new APIException("The list of master students is empty");
			
			return masterStudents;
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	@Override
	public MasterStudent get(int id) {
		try {
			Optional<MasterStudent> opt = rep.findById(id);
			if(opt.isEmpty())
				throw new APIException("There is no master student that has an ID of "+id);
			return opt.get();
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public String save(@Autowired MasterStudent masterStudent, int aid, int cid) {
		try {
			if(!rep.findById(masterStudent.getId()).isEmpty())
				throw new APIException("There is another master student that has the same ID"
						+ "\nIf you want to update it go for PUT API");
			
			Optional<Address> optAddress = addressRepo.findById(aid);
			Optional<Courses> optCourse = courseRepo.findById(cid);
			if(optAddress.isEmpty())
				throw new APIException("Address not found");
			if(optCourse.isEmpty())
				throw new APIException("Course not found");
			
			optCourse.get().addMasterStudent(masterStudent);
			optAddress.get().addMasterStudents(masterStudent);
			masterStudent.setAddress(optAddress.get());
			masterStudent.addCourses(optCourse.get());
			rep.save(masterStudent);
			return "The data added successfully";
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	@Override
	public String save(@Autowired MasterStudent masterStudent, int aid) {
		try {
			if(!rep.findById(masterStudent.getId()).isEmpty())
				throw new APIException("There is another master student that has the same ID"
						+ "\nIf you want to update it go for PUT API");
			
			Optional<Address> optAddress = addressRepo.findById(aid);
			if(optAddress.isEmpty())
				throw new APIException("Address not found");
			optAddress.get().addMasterStudents(masterStudent);
			masterStudent.setAddress(optAddress.get());
			rep.save(masterStudent);
			return "The data added successfully";
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	@Override
	public String save(int id, int cid) {
		try {
			MasterStudent masterStudent = rep.findById(id).get();
			
			if(masterStudent == null)
				throw new APIException("Master student not found");
			
			Optional<Courses> optCourse = courseRepo.findById(cid);
		
			if(optCourse.isEmpty())
				throw new APIException("Course not found");
			
			optCourse.get().addMasterStudent(masterStudent);
			masterStudent.addCourses(optCourse.get());
			rep.save(masterStudent);
			return "The data added successfully";
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}

	@Override
	public String update(@Autowired MasterStudent masterStudent) {
		try {
			MasterStudent prevMasterStudent = rep.findById(masterStudent.getId()).get();
			if(prevMasterStudent == null)
				throw new APIException("Master student not found");
			
			Set<String> notIncluded = NoNullProperties.getNullPropertyNames(masterStudent);
			notIncluded.add("address");
			notIncluded.add("courses");
			String[] result = new String[notIncluded.size()];
			BeanUtils.copyProperties(masterStudent, prevMasterStudent, notIncluded.toArray(result));
			
			rep.save(prevMasterStudent);
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
			if(rep.findById(id).isEmpty())
				throw new APIException("Master student not found");
			rep.deleteById(id);
			return "The data has been deleted";
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	@Override
	public String drop(int id, int cId) {
		try {
			MasterStudent masterStudent = rep.findById(id).get();
			if(masterStudent == null)
				throw new APIException("Master student not found");
			
			List<Courses> courses = masterStudent.getCourses();
			int det = 0;
			
			for(int i=0;i<courses.size();i++)
				if(courses.get(i).getId() == cId) {
					courses.remove(i);
					det = 1;
				}
			
			if(det == 1)
				return "Done successfully";
			
			throw new APIException("Course not found");
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
	
	@Override
	public String deleteAll() {
		try {
			if(rep.findAll().isEmpty())
				throw new APIException("The list is empty");
			rep.deleteAll();
			return "The data has been deleted";
		}catch(Exception e) {
			if(e instanceof APIException)
				throw e;
			throw new APIException(e.getMessage());
		}
	}
}
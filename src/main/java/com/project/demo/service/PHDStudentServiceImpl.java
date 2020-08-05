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
import com.project.demo.model.Courses;
import com.project.demo.model.PHDStudent;
import com.project.demo.repository.AddressRepo;
import com.project.demo.repository.CoursesRepo;
import com.project.demo.repository.PHDStudentRepo;

@Service
public class PHDStudentServiceImpl implements PHDStudentService {
	@Autowired
	PHDStudentRepo rep;
	
	@Autowired
	private AddressRepo addressRepo;
	
	@Autowired
	private CoursesRepo courseRepo;
	
	@Override
	public List<PHDStudent> getPHDStudents() {
		List<PHDStudent> phdStudents = rep.findAll();
		if(phdStudents.isEmpty())
			throw new APIException("The list of PHD students is empty");
		return phdStudents;
	}
	
	public Page<PHDStudent> get(String name, Pageable pageable){
		Page<PHDStudent> page = rep.findByName(name, pageable);
		
		if(page.isEmpty())
			throw new APIException("The list of PHD students is empty");
		
		return page;
	}
	
	public List<PHDStudent> getAll(Pageable pageable){
		Page<PHDStudent> page = rep.findAll(pageable);
		
		if(page.isEmpty())
			throw new APIException("The list of PHD students is empty");
		
		return page.toList();
	}

	@Override
	public List<PHDStudent> get(String name){
		List<PHDStudent> phdStudents = rep.findByName(name);
		
		if(phdStudents.isEmpty())
			throw new APIException("The list of PHD students is empty");
		
		return phdStudents;
	}
	
	@Override
	public List<PHDStudent> getDesc(String name){
		List<PHDStudent> phdStudents = rep.findByNameDesc(name);
		
		if(phdStudents.isEmpty())
			throw new APIException("The list of PHD students is empty");
		
		return phdStudents;
	}
	
	@Override
	public PHDStudent get(int id) {
		Optional<PHDStudent> opt = rep.findById(id);
		if(opt.isEmpty())
			throw new APIException("There is no PHD student that has an ID of "+id);
		return opt.get();
	}

	@Override
	public String save(@Autowired PHDStudent phdStudent, int aid, int cid) {
		if(!rep.findById(phdStudent.getId()).isEmpty())
			throw new APIException("There is another PHD student that has the same ID"
					+ "\nIf you want to update it go for PUT API");
		
		Optional<Address> optAddress = addressRepo.findById(aid);
		Optional<Courses> optCourse = courseRepo.findById(cid);
		if(optAddress.isEmpty())
			throw new APIException("Address not found");
		if(optCourse.isEmpty())
			throw new APIException("Course not found");
		
		optCourse.get().addPhdStudents(phdStudent);
		optAddress.get().addPhdStudents(phdStudent);
		phdStudent.setAddress(optAddress.get());
		phdStudent.addCourses(optCourse.get());
		rep.save(phdStudent);
		return "The data added successfully";
	}
	
	@Override
	public String save(@Autowired PHDStudent phdStudent, int aid) {
		if(!rep.findById(phdStudent.getId()).isEmpty())
			throw new APIException("There is another PHD student that has the same ID"
					+ "\nIf you want to update it go for PUT API");
		
		Optional<Address> optAddress = addressRepo.findById(aid);
		if(optAddress.isEmpty())
			throw new APIException("Address not found");
		optAddress.get().addPhdStudents(phdStudent);
		phdStudent.setAddress(optAddress.get());
		rep.save(phdStudent);
		return "The data added successfully";
	}
	
	@Override
	public String save(int id, int cid) {
		PHDStudent phdStudent = rep.findById(id).get();
		
		if(phdStudent == null)
			throw new APIException("PHD student not found");
		
		Optional<Courses> optCourse = courseRepo.findById(cid);

		if(optCourse.isEmpty())
			throw new APIException("Course not found");
		
		optCourse.get().addPhdStudents(phdStudent);
		phdStudent.addCourses(optCourse.get());
		rep.save(phdStudent);
		return "The data added successfully";
	}

	@Override
	public String update(@Autowired PHDStudent pHDStudent) {
		PHDStudent prevPHDStudent = rep.findById(pHDStudent.getId()).get();
		if(prevPHDStudent == null)
			throw new APIException("PHD student not found");
		BeanUtils.copyProperties(pHDStudent, prevPHDStudent);
		rep.save(pHDStudent);
		return "Done successfully";
	}

	@Override
	public String delete(int id) {
		if(rep.findById(id).isEmpty())
			throw new APIException("PHD student not found");
		rep.deleteById(id);
		return "The data has been deleted";
	}
	
	@Override
	public String drop(int id, int cId) {
		PHDStudent phdStudent = rep.findById(id).get();
		if(phdStudent == null)
			throw new APIException("PHD student not found");
		
		List<Courses> courses = phdStudent.getCourses();
		int det = 0;
		
		for(int i=0;i<courses.size();i++)
			if(courses.get(i).getId() == cId) {
				courses.remove(i);
				det = 1;
			}
		
		if(det == 1)
			return "Done successfully";
		
		throw new APIException("Course not found");
	}

	@Override
	public String deleteAll() {
		if(rep.findAll().isEmpty())
			throw new APIException("The list is empty");
		rep.deleteAll();
		return "The data has been deleted";
	}
}
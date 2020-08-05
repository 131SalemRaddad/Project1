package com.project.demo.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.demo.repository.AddressRepo;
import com.project.demo.repository.BachelorStudentRepo;
import com.project.demo.repository.CoursesRepo;
import com.project.demo.exception.APIException;
import com.project.demo.model.Address;
import com.project.demo.model.BachelorStudent;
import com.project.demo.model.Courses;

@Service
public class BachelorStudentServiceImpl implements BachelorStudentService{
	@Autowired
	private BachelorStudentRepo rep;
	
	@Autowired
	private AddressRepo addressRepo;
	
	@Autowired
	private CoursesRepo courseRepo;

	@Override
	public List<BachelorStudent>  getBachelorStudents() {
		List<BachelorStudent> bachelorStudents = rep.findAll();
		if(bachelorStudents.isEmpty())
			throw new APIException("The list of bachelor students is empty");
		return bachelorStudents;
	}
	
	public Page<BachelorStudent> get(String name, Pageable pageable){
		Page<BachelorStudent> page = rep.findByName(name, pageable);
		
		if(page.isEmpty())
			throw new APIException("The list of bachelor students is empty");
		
		return page;
	}
	
	public List<BachelorStudent> getAll(Pageable pageable){
		Page<BachelorStudent> page = rep.findAll(pageable);
		
		if(page.isEmpty())
			throw new APIException("The list of bachelor students is empty");
		
		return page.toList();
	}
	
	@Override
	public List<BachelorStudent> get(String name){
		List<BachelorStudent> bachelorStudents = rep.findByName(name);
		
		if(bachelorStudents.isEmpty())
			throw new APIException("The list of bachelor students is empty");
		
		return bachelorStudents;
	}
	
	@Override
	public List<BachelorStudent> getDesc(String name){
		List<BachelorStudent> bachelorStudents = rep.findByNameDesc(name);
		
		if(bachelorStudents.isEmpty())
			throw new APIException("The list of bachelor students is empty");
		
		return bachelorStudents;
	}

	@Override
	public BachelorStudent get(int id) {
		Optional<BachelorStudent> opt = rep.findById(id);
		if(opt.isEmpty())
			throw new APIException("Bachelor student not found");
		return opt.get();
	}

	@Override
	public String save(@Autowired BachelorStudent bachelorStudent, int aId, int cId) {
		if(!rep.findById(bachelorStudent.getId()).isEmpty())
			throw new APIException("There is another bachelor student that has the same ID"
					+ "\nIf you want to update it go for PUT API");
		
		Optional<Address> optAddress = addressRepo.findById(aId);
		Optional<Courses> optCourse = courseRepo.findById(cId);
		
		if(optAddress.isEmpty())
			throw new APIException("Address not found");
		
		if(optCourse.isEmpty())
			throw new APIException("Course not found");
		
		optAddress.get().addBachelorStudents(bachelorStudent);
		optCourse.get().addBachelorStudent(bachelorStudent);
		bachelorStudent.setAddress(optAddress.get());
		bachelorStudent.addCourses(optCourse.get());
		
		rep.save(bachelorStudent);
		return "The data added successfully";
	}
	
	@Override
	public String save(@Autowired BachelorStudent bachelorStudent, int aid) {
		if(!rep.findById(bachelorStudent.getId()).isEmpty())
			throw new APIException("There is another bachelor student that has the same ID"
					+ "\nIf you want to update it go for PUT API");
		
		Optional<Address> optAddress = addressRepo.findById(aid);
		if(optAddress.isEmpty())
			throw new APIException("Address not found");
		optAddress.get().addBachelorStudents(bachelorStudent);
		bachelorStudent.setAddress(optAddress.get());
		rep.save(bachelorStudent);
		return "The data added successfully";
	}
	
	@Override
	public String save(int id, int cid) {
		BachelorStudent bachelorStudent = rep.findById(id).get();
		
		if(bachelorStudent == null)
			throw new APIException("Bachelor student not found");
		
		Optional<Courses> optCourse = courseRepo.findById(cid);

		if(optCourse.isEmpty())
			throw new APIException("Course not found");
		
		optCourse.get().addBachelorStudent(bachelorStudent);
		bachelorStudent.addCourses(optCourse.get());
		
		rep.save(bachelorStudent);
		return "The data added successfully";
	}

	@Override
	public String update(@Autowired BachelorStudent bachelorStudent) {
		BachelorStudent prevBachelorStudent = rep.findById(bachelorStudent.getId()).get();
		if(prevBachelorStudent == null)
			throw new APIException("Bachelor student not found");
		BeanUtils.copyProperties(bachelorStudent,prevBachelorStudent);
		rep.save(bachelorStudent);
		return "Done successfully";
	}

	@Override
	public String delete(int id) {
		if(rep.findById(id).isEmpty())
			throw new APIException("Bachelor student not found");
		
		rep.deleteById(id);
		return "The data has been deleted";
	}
	
	@Override
	public String drop(int id, int cId) {
		BachelorStudent bachelorStudent = rep.findById(id).get();
		if(bachelorStudent == null)
			throw new APIException("Bachelor student not found");
		
		List<Courses> courses = bachelorStudent.getCourses();
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
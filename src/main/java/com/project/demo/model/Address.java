package com.project.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table
@Component
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Address {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	 
	@OneToMany(fetch = FetchType.LAZY, targetEntity = BachelorStudent.class, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@JoinColumn(name = "ab_fk", referencedColumnName = "id")
	private List<BachelorStudent> bachelorStudents = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = MasterStudent.class, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@JoinColumn(name = "am_fk", referencedColumnName = "id")
	private List<MasterStudent> masterStudents = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = PHDStudent.class, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@JoinColumn(name = "ap_fk", referencedColumnName = "id")
	private List<PHDStudent> phdStudents = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = Instructor.class, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@JoinColumn(name = "ai_fk", referencedColumnName = "id")
	private List<Instructor> instructors = new ArrayList<>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<BachelorStudent> getBachelorStudents() {
		return bachelorStudents;
	}
	public void addBachelorStudents(BachelorStudent bachelorStudent) {
		this.bachelorStudents.add(bachelorStudent);
	}
	public List<MasterStudent> getMasterStudents() {
		return masterStudents;
	}
	public void addMasterStudents(MasterStudent masterStudent) {
		this.masterStudents.add(masterStudent);
	}
	public List<PHDStudent> getPhdStudents() {
		return phdStudents;
	}
	public void addPhdStudents(PHDStudent phdStudent) {
		this.phdStudents.add(phdStudent);
	}
	public List<Instructor> getInstructors() {
		return instructors;
	}
	public void addInstructors(Instructor instructor) {
		this.instructors.add(instructor);
	}
	@Override
	public String toString() {
		return "Address [id=" + id + ", name=" + name + "]\n";
	}
}
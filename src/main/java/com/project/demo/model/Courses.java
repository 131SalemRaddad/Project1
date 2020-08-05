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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@DynamicUpdate
@Entity
@Component
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Courses {
	@Id
	@GeneratedValue
	private int id;
	private String name;
//	private String department;
	private int hours;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "courses", cascade = CascadeType.PERSIST)
	@Column(updatable = false)
	private List<BachelorStudent> bachelorStudents = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "courses")
	@Column(updatable = false)
	private List<MasterStudent> masterStudents = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "courses")
	@Column(updatable = false)
	private List<PHDStudent> phdStudents = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY, targetEntity=Instructor.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name="ci_fk")
//	@Column(updatable = false)
	private Instructor instructor;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity=Instructor.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name="cd_fk")
//	@Column(updatable = false)
	private Department department;
	
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
	public Department getDepartment() {
		return department;
	}

	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public List<BachelorStudent> getBachelorStudents() {
		return bachelorStudents;
	}
	public void addBachelorStudent(BachelorStudent bachelorStudent) {
		this.bachelorStudents.add(bachelorStudent);
	}
	public List<MasterStudent> getMasterStudents() {
		return masterStudents;
	}
	public void addMasterStudent(MasterStudent masterStudent) {
		this.masterStudents.add(masterStudent);
	}
	public List<PHDStudent> getPhdStudents() {
		return phdStudents;
	}
	public void addPhdStudents(PHDStudent phdStudent) {
		this.phdStudents.add(phdStudent);
	}
	public Instructor getInstructor() {
		return instructor;
	}
	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	@Override
	public String toString() {
		return "Courses [id=" + id + ", name=" + name + ", department=" + department + ", hours=" + hours + "]\n";
	}
}
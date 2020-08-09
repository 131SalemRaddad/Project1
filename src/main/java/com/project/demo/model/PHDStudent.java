package com.project.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table
@Component
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PHDStudent {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private double bachelorGPA;
	private double masterGPA;
//	private String city;
	private String college;
	private int startYear;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=Address.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name="pa_fk")
	private Address address;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	  name = "course_phd", 
	  joinColumns = @JoinColumn(name = "phdStudent_id"), 
	  inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Courses> courses = new ArrayList<>();
	
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
	public double getBachelorGPA() {
		return bachelorGPA;
	}
	public void setBachelorGPA(double bachelorGPA) {
		this.bachelorGPA = bachelorGPA;
	}
	public double getMasterGPA() {
		return masterGPA;
	}
	public void setMasterGPA(double masterGPA) {
		this.masterGPA = masterGPA;
	}
//	public String getCity() {
//		return city;
//	}
//	public void setCity(String city) {
//		this.city = city;
//	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public int getStartYear() {
		return startYear;
	}
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<Courses> getCourses() {
		return courses;
	}
	public void addCourses(Courses course) {
		this.courses.add(course);
	}
	@Override
	public String toString() {
		return "PHDStudent [id=" + id + ", name=" + name + ", bachelorGPA=" + bachelorGPA + ", masterGPA=" + masterGPA
				+ ", college=" + college + ", startYear=" + startYear + "]\n";
	}
}
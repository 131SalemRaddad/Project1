package com.project.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Component
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BachelorStudent {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private double tawjihiGPA;
//	private String city;
	private String college;
	private int startYear;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=Address.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name="ba_fk")
	private Address address;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	  name = "course_in", joinColumns = @JoinColumn(name = "bachelorStudent_id"),
	  inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Courses> courses = new ArrayList<>();
	
	public List<Courses> getCourses() {
		return courses;
	}
	public void addCourses(Courses course) {
		this.courses.add(course);
	}
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
	public double getTawjihiGPA() {
		return tawjihiGPA;
	}
	public void setTawjihiGPA(double tawjihiGPA) {
		this.tawjihiGPA = tawjihiGPA;
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
	@Override
	public String toString() {
		return "BachelorStudent [id=" + id + ", name=" + name + ", tawjihiGPA=" + tawjihiGPA
				+ ", college=" + college + ", startYear=" + startYear + ", address=" + address + "]";
	}
}
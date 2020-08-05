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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Instructor {
	@Id
	@GeneratedValue
	private int id;
	private String name;
//	private String department;
	private String gradeLevel;
	private String collegeName;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity=Address.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name="ia_fk")
//	@Column(updatable = false)
	private Address address;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity=Department.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_fk")
//	@Column(updatable = false)
	private Department department;

	@OneToMany(fetch = FetchType.LAZY, targetEntity = Courses.class, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@JoinColumn(name = "ic_fk", referencedColumnName = "id")
	@Column(updatable = false)
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
	public Department getDepartment() {
		return department;
	}
	
	public String getGradeLevel() {
		return gradeLevel;
	}
	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
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
	public void setDepartment(Department department) {
		this.department = department;
	}
	@Override
	public String toString() {
		return "Instructor [id=" + id + ", name=" + name + ", department=" + department + ", gradeLevel=" + gradeLevel
				+ ", collegeName=" + collegeName + "]\n";
	}
}
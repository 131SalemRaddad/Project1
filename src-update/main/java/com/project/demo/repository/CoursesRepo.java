package com.project.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.demo.model.Courses;
@Repository
public interface CoursesRepo extends JpaRepository<Courses,Integer> {
	//Select based on name
	@Query("FROM Courses WHERE name = ?1")
	public List<Courses> findByName(String name);
	
	//Select based on name sorted
	@Query("FROM Courses WHERE name = ?1 ORDER BY id DESC")
	public List<Courses> findByNameDesc(String name);
	
	//Paging select based on name
	@Query("select c from Courses c where c.name = ?1")
	public Page<Courses> findByName(String name, Pageable pageable);
	
	//Paging get all
	@Query(value = "select * from Courses c", nativeQuery = true)
	public Page<Courses> findAll(Pageable pageable);
}
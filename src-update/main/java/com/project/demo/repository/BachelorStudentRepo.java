package com.project.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.demo.model.BachelorStudent;
import com.project.demo.model.Courses;
@Repository
public interface BachelorStudentRepo extends JpaRepository<BachelorStudent,Integer> {
	//Select based on name
	@Query("FROM BachelorStudent WHERE name = ?1")
	public List<BachelorStudent> findByName(String name);
	
	//Select based on name sorted
	@Query("FROM BachelorStudent WHERE name = ?1 ORDER BY id DESC")
	public List<BachelorStudent> findByNameDesc(String name);
	
	//Paging select based on name
	@Query("select b from BachelorStudent b where b.name = ?1")
	public Page<BachelorStudent> findByName(String name, Pageable pageable);
	
	//Paging get all
	@Query(value = "select * from bachelor_student b", nativeQuery = true)
	public Page<BachelorStudent> findAll(Pageable pageable);

	//Select courses based on name
	@Query(value = "select c from Courses c where c.name = ?1")
	public List<Courses> getCoursesByName(String name);
}
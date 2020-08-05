package com.project.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.demo.model.Instructor;

@Repository
public interface InstructorRepo extends JpaRepository<Instructor, Integer> {
	//Select based on name
	@Query("FROM Instructor WHERE name = ?1")
	public List<Instructor> findByName(String name);
	
	//Select based on name sorted
	@Query("FROM Instructor WHERE name = ?1 ORDER BY id DESC")
	public List<Instructor> findByNameDesc(String name);
	
	//Paging select based on name
	@Query("select i from Instructor i where i.name = ?1")
	public Page<Instructor> findByName(String name, Pageable pageable);
	
	//Paging get all
	@Query(value = "select * from Instructor i", nativeQuery = true)
	public Page<Instructor> findAll(Pageable pageable);
}
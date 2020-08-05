package com.project.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.demo.model.PHDStudent;

@Repository
public interface PHDStudentRepo extends JpaRepository<PHDStudent, Integer>{
	//Select based on name
	@Query("FROM PHDStudent WHERE name = ?1")
	public List<PHDStudent> findByName(String name);
	
	//Select based on name sorted
	@Query("FROM PHDStudent WHERE name = ?1 ORDER BY id DESC")
	public List<PHDStudent> findByNameDesc(String name);
	
	//Paging select based on name
	@Query("select p from PHDStudent p where p.name = ?1")
	public Page<PHDStudent> findByName(String name, Pageable pageable);
	
	//Paging get all
	@Query(value = "select * from PHDStudent p", nativeQuery = true)
	public Page<PHDStudent> findAll(Pageable pageable);
}
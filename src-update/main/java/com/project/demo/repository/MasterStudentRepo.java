package com.project.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.demo.model.MasterStudent;

@Repository
public interface MasterStudentRepo extends JpaRepository<MasterStudent, Integer> {
	//Select based on name
	@Query("FROM MasterStudent WHERE name = ?1")
	public List<MasterStudent> findByName(String name);
	
	//Select based on name sorted
	@Query("FROM MasterStudent WHERE name = ?1 ORDER BY id DESC")
	public List<MasterStudent> findByNameDesc(String name);
	
	//Paging select based on name
	@Query("select m from MasterStudent m where m.name = ?1")
	public Page<MasterStudent> findByName(String name, Pageable pageable);
	
	//Paging get all
	@Query(value = "select * from master_student m", nativeQuery = true)
	public Page<MasterStudent> findAll(Pageable pageable);
}
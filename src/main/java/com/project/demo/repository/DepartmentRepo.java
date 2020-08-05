package com.project.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.demo.model.Department;
@Repository
public interface DepartmentRepo extends JpaRepository<Department,Integer> {
	//Select based on name
	@Query("FROM Department WHERE name = ?1")
	public List<Department> findByName(String name);
	
	//Select based on name sorted
	@Query("FROM Department WHERE name = ?1 ORDER BY id DESC")
	public List<Department> findByNameDesc(String name);
	
	//Paging select based on name
	@Query("select d from Department d where d.name = ?1")
	public Page<Department> findByName(String name, Pageable pageable);
	
	//Paging get all
	@Query(value = "select * from Department d", nativeQuery = true)
	public Page<Department> findAll(Pageable pageable);
}
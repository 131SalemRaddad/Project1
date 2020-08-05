package com.project.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.demo.model.Address;
@Repository
public interface AddressRepo extends JpaRepository<Address,Integer> {
	//Select based on name
	@Query("FROM Address WHERE name = ?1")
	public List<Address> findByName(String name);
	
	//Select based on name sorted
	@Query("FROM Address WHERE name = ?1 ORDER BY id DESC")
	public List<Address> findByNameDesc(String name);
	
	//Paging select based on name
	@Query("select a from Address a where a.name = ?1")
	public Page<Address> findByName(String name, Pageable pageable);
	
	//Paging get all
	@Query(value = "select * from Address a", nativeQuery = true)
	public Page<Address> findAll(Pageable pageable);
}
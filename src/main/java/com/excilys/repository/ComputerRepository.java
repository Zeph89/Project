package com.excilys.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.beans.Computer;

public interface ComputerRepository extends JpaRepository<Computer, Integer> {

	Computer findOne(Integer primaryKey);
	
	Page<Computer> findAllByNameLike(String search, Pageable p);
	
	Page<Computer> findAllByNameLikeIgnoreCase(String search, Pageable p);
}
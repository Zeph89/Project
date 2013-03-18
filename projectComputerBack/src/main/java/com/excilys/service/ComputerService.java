package com.excilys.service;

import com.excilys.dao.ComputerDAO;
import org.springframework.data.domain.Page;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;
import org.springframework.data.domain.Pageable;

public interface ComputerService {

	void create(Computer computer);

	Computer findById(int id);

	Page<Computer> list(Pageable pageable, String searchComputer, String searchCompany);

	void delete(int id);
	
	void update(Computer oldComputer, String newName, String newIntroducedDate, String newDiscontinuedDate, Company newCompany);

}

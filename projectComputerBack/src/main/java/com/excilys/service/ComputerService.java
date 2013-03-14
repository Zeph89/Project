package com.excilys.service;

import org.springframework.data.domain.Page;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;

public interface ComputerService {

	void create(Computer computer);

	Computer findById(int id);

	Page<Computer> list(int start, int size);
	
	Page<Computer> list(int start, int size, int sort);

	Page<Computer> list(int start, int size, String searchComputer, String searchCompany);
	
	Page<Computer> list(int start, int size, String searchComputer, String searchCompany, int sort);

	void delete(int id);
	
	void update(Computer oldComputer, String newName, String newIntroducedDate, String newDiscontinuedDate, Company newCompany);

}

package com.excilys.service;

import java.util.List;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;

public interface ComputerService {

	void create(Computer computer);

	Computer findById(int id);

	List<Computer> list(int start, int size);
	
	List<Computer> list(int start, int size, int sort);
	
	List<Computer> list(int start, int size, String search);
	
	List<Computer> list(int start, int size, String search, int sort);
	
	int getNumberComputers();
	
	int getNumberComputers(String search);

	void delete(int id);
	
	void update(Computer oldComputer, String newName, String newIntroducedDate, String newDiscontinuedDate, Company newCompany);

}

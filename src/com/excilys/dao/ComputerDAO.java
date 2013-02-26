package com.excilys.dao;

import java.util.List;

import com.excilys.beans.Computer;

public interface ComputerDAO {
	
	void create(Computer computer) throws DAOException;

	Computer findById(int id) throws DAOException;

	List<Computer> list() throws DAOException;
	
	List<Computer> list(String search) throws DAOException;

	void delete(int id) throws DAOException;
	
	void update(Computer oldComputer, String newName, String newIntroducedDate, String newDiscontinuedDate, int newCompanyId) throws DAOException;
}

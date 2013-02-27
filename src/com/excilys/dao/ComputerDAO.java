package com.excilys.dao;

import java.util.List;

import com.excilys.beans.Computer;

public interface ComputerDAO {
	
	void create(Computer computer) throws DAOException;

	Computer findById(int id) throws DAOException;

	List<Computer> list(int start, int size) throws DAOException;
	
	List<Computer> list(int start, int size, int sort) throws DAOException;
	
	List<Computer> list(int start, int size, String search) throws DAOException;
	
	List<Computer> list(int start, int size, String search, int sort) throws DAOException;
	
	int getNumberComputers();
	
	int getNumberComputers(String search);

	void delete(int id) throws DAOException;
	
	void update(Computer oldComputer, String newName, String newIntroducedDate, String newDiscontinuedDate, int newCompanyId) throws DAOException;
}

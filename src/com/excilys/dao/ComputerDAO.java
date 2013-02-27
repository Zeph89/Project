package com.excilys.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.beans.Computer;

public interface ComputerDAO {
	
	void create(Computer computer, Connection connection) throws DAOException, SQLException;

	Computer findById(int id) throws DAOException;

	List<Computer> list(int start, int size) throws DAOException;
	
	List<Computer> list(int start, int size, int sort) throws DAOException;
	
	List<Computer> list(int start, int size, String search) throws DAOException;
	
	List<Computer> list(int start, int size, String search, int sort) throws DAOException;
	
	int getNumberComputers();
	
	int getNumberComputers(String search);

	void delete(int id, Connection connection) throws DAOException, SQLException;
	
	void update(Computer oldComputer, String newName, String newIntroducedDate, String newDiscontinuedDate, int newCompanyId, Connection connection) throws DAOException, SQLException;
}

package com.excilys.dao;

import java.util.List;

import com.excilys.beans.Computer;

public interface ComputerDAO {
	
	void create(Computer computer) throws DAOException;

	Computer findById(int id) throws DAOException;

	List<Computer> list() throws DAOException;

	void delete(Computer computer) throws DAOException;
}

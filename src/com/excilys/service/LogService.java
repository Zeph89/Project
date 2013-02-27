package com.excilys.service;

import java.util.List;

import com.excilys.beans.Computer;
import com.excilys.beans.Log;
import com.excilys.dao.DAOException;

public interface LogService {

	void create(Log log) throws DAOException;

	Log findById(int id) throws DAOException;

	List<Log> list() throws DAOException;
	
	List<Log> list(Computer c) throws DAOException;
}

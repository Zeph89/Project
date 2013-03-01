package com.excilys.service;

import java.util.List;

import com.excilys.beans.Computer;
import com.excilys.beans.Log;
import com.excilys.dao.DAOException;
import com.excilys.dao.DAOFactory;
import com.excilys.dao.LogDAO;

public enum LogServiceImpl implements LogService {
	
	INSTANCE;
	
	private LogDAO lg = DAOFactory.INSTANCE.getLg();
	
	public void create(Log log) throws DAOException {
		lg.create(log);
	}

	public Log findById(int id) throws DAOException {
		return lg.findById(id);
	}

	public List<Log> list() throws DAOException {
		return lg.list();
	}

	public List<Log> list(Computer c) throws DAOException {
		return list(c);
	}
}

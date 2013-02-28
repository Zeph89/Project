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
	
	@Override
	public void create(Log log) throws DAOException {
		lg.create(log);
	}

	@Override
	public Log findById(int id) throws DAOException {
		return lg.findById(id);
	}

	@Override
	public List<Log> list() throws DAOException {
		return lg.list();
	}

	@Override
	public List<Log> list(Computer c) throws DAOException {
		return list(c);
	}
}

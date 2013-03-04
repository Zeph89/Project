package com.excilys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.beans.Computer;
import com.excilys.beans.Log;
import com.excilys.dao.DAOException;
import com.excilys.dao.LogDAO;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogDAO lg;
	
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

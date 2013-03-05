package com.excilys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.beans.Log;
import com.excilys.dao.LogDAO;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogDAO lg;
	
	public void create(Log log) {
		lg.create(log);
	}
}

package com.excilys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.beans.Log;
import com.excilys.dao.LogDAO;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogDAO lg;
	
	@Transactional
	public void create(Log log) {
		lg.create(log);
	}
}

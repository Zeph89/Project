package com.excilys.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.beans.Log;
import com.excilys.repository.LogRepository;

@Repository("LogDaoImpl")
public class LogDaoImpl implements LogDAO {

	@Autowired
	private LogRepository logRepository;
	
	public void create(Log log) {
		logRepository.save(log);
	}
}

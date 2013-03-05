package com.excilys.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.beans.Log;

@Repository("LogDaoImpl")
public class LogDaoImpl implements LogDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void create(Log log) {
		jdbcTemplate.update("INSERT INTO log (description, computer_id, computer_name, log_date) VALUES (?, ?, ?, ?)",
			        new Object[] { log.getDescription(), log.getComputerId(), log.getComputerName(), log.getDate() });
	}
}

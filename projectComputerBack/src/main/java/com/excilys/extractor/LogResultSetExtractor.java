package com.excilys.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.excilys.beans.Log;

public class LogResultSetExtractor implements ResultSetExtractor<Log> {

	public Log extractData(ResultSet rs) throws SQLException {
		Log log = new Log();
		log.setId(rs.getInt("id"));
		log.setDescription(rs.getString("description"));
		log.setComputerId(rs.getInt("computer_id"));
		log.setComputerName(rs.getString("computer_name"));
		log.setDate(rs.getDate("log_date"));
		
		return log;
	}

}

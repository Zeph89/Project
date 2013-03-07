package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.beans.Log;
import com.excilys.extractor.LogResultSetExtractor;

public class LogRowMapper implements RowMapper<Log> {

	public Log mapRow(ResultSet rs, int line) throws SQLException {
		LogResultSetExtractor extractor = new LogResultSetExtractor();
		return extractor.extractData(rs);
	}

}

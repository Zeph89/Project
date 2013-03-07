package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.beans.Computer;
import com.excilys.extractor.ComputerResultSetExtractor;

public class ComputerRowMapper implements RowMapper<Computer> {

	public Computer mapRow(ResultSet rs, int line) throws SQLException {
		ComputerResultSetExtractor extractor = new ComputerResultSetExtractor();
		return extractor.extractData(rs);
	}

}

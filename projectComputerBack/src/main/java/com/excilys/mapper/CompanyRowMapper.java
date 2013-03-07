package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.beans.Company;
import com.excilys.extractor.CompanyResultSetExtractor;

public class CompanyRowMapper implements RowMapper<Company> {

	public Company mapRow(ResultSet rs, int line) throws SQLException {
		CompanyResultSetExtractor extractor = new CompanyResultSetExtractor();
		return extractor.extractData(rs);
	}

}

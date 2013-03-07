package com.excilys.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.excilys.beans.Company;

public class CompanyResultSetExtractor implements ResultSetExtractor<Company> {

	public Company extractData(ResultSet rs) throws SQLException {
		Company company = new Company();
		company.setId(rs.getInt("id"));
		company.setName(rs.getString("name"));

		return company;
	}

}

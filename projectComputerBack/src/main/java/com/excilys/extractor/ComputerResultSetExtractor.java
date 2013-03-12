package com.excilys.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;

public class ComputerResultSetExtractor implements ResultSetExtractor<Computer> {

	public Computer extractData(ResultSet rs) throws SQLException {
		Company company = new Company();
		company.setId(rs.getInt("company_id"));
		company.setName(rs.getString("company_name"));

		Computer computer = new Computer();
		computer.setId(rs.getInt("id"));
		computer.setName(rs.getString("name"));
		computer.setIntroducedDate(new DateTime(rs.getDate("introduced_date")));
		computer.setDiscontinuedDate(new DateTime(rs.getDate("discontinued_date")));
		computer.setCompany(company);

		return computer;
	}

}
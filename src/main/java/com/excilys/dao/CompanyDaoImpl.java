package com.excilys.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.beans.Company;
import com.excilys.mapper.CompanyRowMapper;

@Repository("CompanyDaoImpl")
public class CompanyDaoImpl implements CompanyDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Company findById(int id) {
		return jdbcTemplate.queryForObject("SELECT id, name FROM company WHERE id = ?",
		            new Object[] { id },
		            new CompanyRowMapper());
	}

	public List<Company> list() {
		return jdbcTemplate.query("SELECT id, name FROM company",
	            new CompanyRowMapper());
	}
}

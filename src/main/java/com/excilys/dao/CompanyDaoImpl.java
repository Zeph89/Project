package com.excilys.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.beans.Company;
import com.excilys.repository.CompanyRepository;

@Repository("CompanyDaoImpl")
public class CompanyDaoImpl implements CompanyDAO {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	public Company findById(int id) {
		return companyRepository.findOne(id);
	}

	public List<Company> list() {
		return companyRepository.findAll();
	}
}

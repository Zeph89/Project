package com.excilys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.beans.Company;
import com.excilys.dao.CompanyDAO;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	private CompanyDAO cy;
	
	@Transactional(readOnly=true)
	public Company findById(int id) {
		return cy.findById(id);
	}

	@Transactional(readOnly=true)
	public List<Company> list() {
		return cy.list();
	}
}

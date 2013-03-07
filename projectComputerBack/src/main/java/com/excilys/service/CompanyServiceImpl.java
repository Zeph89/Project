package com.excilys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.beans.Company;
import com.excilys.dao.CompanyDAO;

@Service
@Transactional(readOnly=true)
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	private CompanyDAO cy;
	
	public Company findById(int id) {
		return cy.findById(id);
	}

	public List<Company> list() {
		return cy.list();
	}
}

package com.excilys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.beans.Company;
import com.excilys.dao.CompanyDAO;
import com.excilys.dao.DAOException;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	private CompanyDAO cy;
	
	public void create(Company company) throws DAOException {
		cy.create(company);
	}

	public Company findById(int id) throws DAOException {
		return cy.findById(id);
	}

	public List<Company> list() throws DAOException {
		return cy.list();
	}

	public void delete(int id) throws DAOException {
		cy.delete(id);
	}

	
}

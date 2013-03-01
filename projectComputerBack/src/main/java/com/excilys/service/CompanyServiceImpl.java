package com.excilys.service;

import java.util.List;

import com.excilys.beans.Company;
import com.excilys.dao.CompanyDAO;
import com.excilys.dao.DAOException;
import com.excilys.dao.DAOFactory;

public enum CompanyServiceImpl implements CompanyService {
	
	INSTANCE;

	private CompanyDAO cy = DAOFactory.INSTANCE.getCy();
	
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

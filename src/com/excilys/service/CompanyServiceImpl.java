package com.excilys.service;

import java.util.List;

import com.excilys.beans.Company;
import com.excilys.dao.CompanyDAO;
import com.excilys.dao.DAOException;
import com.excilys.dao.DAOFactory;

public enum CompanyServiceImpl implements CompanyService {
	
	INSTANCE;

	private CompanyDAO cy = DAOFactory.INSTANCE.getCy();
	
	@Override
	public void create(Company company) throws DAOException {
		cy.create(company);
	}

	@Override
	public Company findById(int id) throws DAOException {
		return cy.findById(id);
	}

	@Override
	public List<Company> list() throws DAOException {
		return cy.list();
	}

	@Override
	public void delete(int id) throws DAOException {
		cy.delete(id);
	}

	
}

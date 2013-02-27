package com.excilys.service;

import java.util.List;

import com.excilys.beans.Company;
import com.excilys.dao.CompanyDAO;
import com.excilys.dao.CompanyDaoImpl;
import com.excilys.dao.DAOException;
import com.excilys.dao.DAOFactory;

public class CompanyServiceImpl implements CompanyService {

	private CompanyDAO cy;
	
	public CompanyServiceImpl() {
		this.cy = new CompanyDaoImpl(DAOFactory.getInstance());
	}
	
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

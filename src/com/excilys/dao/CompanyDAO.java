package com.excilys.dao;

import java.util.List;

import com.excilys.beans.Company;

public interface CompanyDAO {
	
	void create(Company company) throws DAOException;

	Company findById(int id) throws DAOException;

	List<Company> list() throws DAOException;

	void delete(Company company) throws DAOException;
}

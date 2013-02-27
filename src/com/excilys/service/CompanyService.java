package com.excilys.service;

import java.util.List;

import com.excilys.beans.Company;
import com.excilys.dao.DAOException;

public interface CompanyService {

	void create(Company company) throws DAOException;

	Company findById(int id) throws DAOException;

	List<Company> list() throws DAOException;

	void delete(int id) throws DAOException;

}

package com.excilys.dao;

import java.util.List;

import com.excilys.beans.Company;

public interface CompanyDAO {
	
	Company findById(int id);

	List<Company> list();
}

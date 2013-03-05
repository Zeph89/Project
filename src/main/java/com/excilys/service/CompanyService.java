package com.excilys.service;

import java.util.List;

import com.excilys.beans.Company;

public interface CompanyService {

	Company findById(int id);

	List<Company> list();
}

package com.excilys.service;

import java.util.List;

import com.excilys.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.beans.Company;

@Service
@Transactional(readOnly=true)
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Secured({"ROLE_USER"})
	public Company findById(int id) {
        return companyRepository.findOne(id);
	}

    @Secured({"ROLE_USER"})
	public List<Company> list() {
        return companyRepository.findAll();
	}
}

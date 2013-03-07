package com.excilys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.beans.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	Company findOne(Integer primaryKey);
}

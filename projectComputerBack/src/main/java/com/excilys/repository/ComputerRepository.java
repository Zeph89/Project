package com.excilys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.beans.Computer;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface ComputerRepository extends JpaRepository<Computer, Integer>, QueryDslPredicateExecutor<Computer> {

    @Override
	Computer findOne(Integer primaryKey);
}
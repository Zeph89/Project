package com.excilys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.beans.Log;

public interface LogRepository extends JpaRepository<Log, Integer> {

}

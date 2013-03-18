package com.excilys.dao;

import com.excilys.beans.Computer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ComputerDAO {
	Page<Computer> list(Pageable pageable, String searchComputer, String searchCompany);
}

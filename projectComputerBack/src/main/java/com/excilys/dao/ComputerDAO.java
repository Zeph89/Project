package com.excilys.dao;

import com.excilys.beans.Computer;
import org.springframework.data.domain.Page;

public interface ComputerDAO {
	Page<Computer> list(int start, int size, String searchComputer, String searchCompany);
	
	Page<Computer> list(int start, int size, String searchComputer, String searchCompany, int sort);
}

package com.excilys.dao;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;
import com.excilys.repository.ComputerRepository;

@Repository("ComputerDaoImpl")
public class ComputerDaoImpl implements ComputerDAO {
	
	@Autowired
	private ComputerRepository computerRepository;
	
	public void create(Computer computer) {
		computerRepository.save(computer);
	}

	public Computer findById(int id) {
		return computerRepository.findOne(id);
	}

	public Page<Computer> list(int start, int size) {
		return computerRepository.findAll(constructPageSpecification(start, size, Sort.Direction.ASC, "name"));
	}
	
	public Page<Computer> list(int start, int size, int sort) {
		Direction d = null;
		if (sort > 0)
			d = Sort.Direction.ASC;
		else
			d = Sort.Direction.DESC;
		
		String column = "";
		if ((sort == 1) || (sort == -1))
			column = "name";
		else if ((sort == 2) || (sort == -2))
			column = "introducedDate";
		else if ((sort == 3) || (sort == -3))
			column = "discontinuedDate";
		else if ((sort == 4) || (sort == -4))
			column = "cy.name";

		return computerRepository.findAll(constructPageSpecification(start, size, d, column));
	}
	
	public int getNumberComputers() {
		return (int) computerRepository.count();
	}
	
	public Page<Computer> list(int start, int size, String search) {
		return computerRepository.findAllByNameLike(search, constructPageSpecification(start, size, Sort.Direction.ASC, "name"));
	}
	
	public Page<Computer> list(int start, int size, String search, int sort) {
		Direction d = null;
		if (sort > 0)
			d = Sort.Direction.ASC;
		else
			d = Sort.Direction.DESC;
		
		String column = "";
		if ((sort == 1) || (sort == -1))
			column = "name";
		else if ((sort == 2) || (sort == -2))
			column = "introducedDate";
		else if ((sort == 3) || (sort == -3))
			column = "discontinuedDate";
		else if ((sort == 4) || (sort == -4))
			column = "cy.name";

		return computerRepository.findAllByNameLike(search, constructPageSpecification(start, size, d, column));
	}
	
	public int getNumberComputers(String search) {
		return (int) computerRepository.count();
	}

	public void delete(int id) {
		Computer computer = findById(id);
		computerRepository.delete(computer);
	}
	
	public void update(Computer oldComputer, String newName, String newIntroducedDate, String newDiscontinuedDate, Company newCompany)  {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		oldComputer.setName(newName);
		
		try {
			if (newIntroducedDate.equals(""))
				oldComputer.setIntroducedDate(null);
			else
				oldComputer.setIntroducedDate(dateFormat.parse(newIntroducedDate));
			
			if (newDiscontinuedDate.equals(""))
				oldComputer.setDiscontinuedDate(null);
			else
				oldComputer.setDiscontinuedDate(dateFormat.parse(newDiscontinuedDate));
		} catch(Exception e) {}
		
		oldComputer.setCompany(newCompany);
		
		computerRepository.save(oldComputer);
	}
	
	private Pageable constructPageSpecification(int start, int size, Direction d, String column) {
        Pageable pageSpecification = new PageRequest(start, size, getSort(d, column));
        return pageSpecification;
    }
	
	private Sort getSort(Direction d, String column) {
        return new Sort(d, column);
    }
}

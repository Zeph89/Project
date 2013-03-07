package com.excilys.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;
import com.excilys.beans.Log;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.LogDAO;

@Service
@Transactional(readOnly=true)
public class ComputerServiceImpl implements ComputerService {

	@Autowired
	private ComputerDAO cp;
	
	@Autowired
	private LogDAO lg;
	
	@Transactional(readOnly=false)
	public void create(Computer computer) {
		cp.create(computer);

		Date now = Calendar.getInstance().getTime();

		Log log = new Log();
		log.setDescription("Insert computer");
		log.setComputerId(computer.getId());
		log.setComputerName(computer.getName());
		log.setDate(now);
		
		lg.create(log);
	}

	
	public Computer findById(int id) {
		return cp.findById(id);
	}

	public Page<Computer> list(int start, int size) {
		return cp.list(start, size);
	}

	public Page<Computer> list(int start, int size, int sort) {
		return cp.list(start, size, sort);
	}

	public Page<Computer> list(int start, int size, String search) {
		return cp.list(start, size, search);
	}

	public Page<Computer> list(int start, int size, String search, int sort) {
		return cp.list(start, size, search, sort);
	}

	@Transactional(readOnly=false)
	public void delete(int id) {
		Computer c = cp.findById(id);
		cp.delete(id);

		Date now = Calendar.getInstance().getTime();

		Log log = new Log();
		log.setDescription("Delete computer");
		log.setComputerId(id);
		log.setComputerName(c.getName());
		log.setDate(now);
		
		lg.create(log);
	}

	@Transactional(readOnly=false)
	public void update(Computer oldComputer, String newName,
			String newIntroducedDate, String newDiscontinuedDate,
			Company newCompany) {
		cp.update(oldComputer, newName, newIntroducedDate, newDiscontinuedDate,
				newCompany);

		Date now = Calendar.getInstance().getTime();

		Log log = new Log();
		log.setDescription("Update computer");
		log.setComputerId(oldComputer.getId());
		log.setComputerName(newName);
		log.setDate(now);
		
		lg.create(log);
	}
}

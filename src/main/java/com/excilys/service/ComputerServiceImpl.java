package com.excilys.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.beans.Computer;
import com.excilys.beans.Log;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.LogDAO;

@Service
public class ComputerServiceImpl implements ComputerService {

	@Autowired
	private ComputerDAO cp;
	
	@Autowired
	private LogDAO lg;
	
	@Transactional
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

	public List<Computer> list(int start, int size) {
		return cp.list(start, size);
	}

	public List<Computer> list(int start, int size, int sort) {
		return cp.list(start, size, sort);
	}

	public List<Computer> list(int start, int size, String search) {
		return cp.list(start, size, search);
	}

	public List<Computer> list(int start, int size, String search, int sort) {
		return cp.list(start, size, search, sort);
	}

	public int getNumberComputers() {
		return cp.getNumberComputers();
	}

	public int getNumberComputers(String search) {
		return cp.getNumberComputers(search);
	}

	@Transactional
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

	@Transactional
	public void update(Computer oldComputer, String newName,
			String newIntroducedDate, String newDiscontinuedDate,
			int newCompanyId) {
		cp.update(oldComputer, newName, newIntroducedDate, newDiscontinuedDate,
				newCompanyId);

		Date now = Calendar.getInstance().getTime();

		Log log = new Log();
		log.setDescription("Update computer");
		log.setComputerId(oldComputer.getId());
		log.setComputerName(newName);
		log.setDate(now);
		
		lg.create(log);
	}
}

package com.excilys.service;

import java.util.List;

import com.excilys.beans.Computer;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ComputerDaoImpl;
import com.excilys.dao.DAOException;
import com.excilys.dao.DAOFactory;


public class ComputerServiceImpl implements ComputerService {

	private ComputerDAO cp;
	
	public ComputerServiceImpl() {
		this.cp = new ComputerDaoImpl(DAOFactory.getInstance());
	}
	
	@Override
	public void create(Computer computer) throws DAOException {
		cp.create(computer);
	}

	@Override
	public Computer findById(int id) throws DAOException {
		return cp.findById(id);
	}

	@Override
	public List<Computer> list(int start, int size) throws DAOException {
		return cp.list(start, size);
	}

	@Override
	public List<Computer> list(int start, int size, int sort)
			throws DAOException {
		return cp.list(start, size, sort);
	}

	@Override
	public List<Computer> list(int start, int size, String search)
			throws DAOException {
		return cp.list(start, size, search);
	}

	@Override
	public List<Computer> list(int start, int size, String search, int sort)
			throws DAOException {
		return cp.list(start, size, search, sort);
	}

	@Override
	public int getNumberComputers() {
		return cp.getNumberComputers();
	}

	@Override
	public int getNumberComputers(String search) {
		return cp.getNumberComputers(search);
	}

	@Override
	public void delete(int id) throws DAOException {
		cp.delete(id);
	}

	@Override
	public void update(Computer oldComputer, String newName,
			String newIntroducedDate, String newDiscontinuedDate,
			int newCompanyId) throws DAOException {
		cp.update(oldComputer, newName, newIntroducedDate, newDiscontinuedDate, newCompanyId);
	}
}

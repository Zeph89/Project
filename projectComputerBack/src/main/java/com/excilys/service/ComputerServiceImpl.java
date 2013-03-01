package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.excilys.beans.Computer;
import com.excilys.beans.Log;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.DAOException;
import com.excilys.dao.DAOFactory;
import com.excilys.dao.DataSourceFactory;
import com.excilys.dao.LogDAO;

public enum ComputerServiceImpl implements ComputerService {

	INSTANCE;
	
	private ComputerDAO cp = DAOFactory.INSTANCE.getCp();
	private LogDAO lg = DAOFactory.INSTANCE.getLg();
	
	public void create(Computer computer) throws DAOException {
		Connection connection = null;
		try {
			connection = DataSourceFactory.INSTANCE.addConnection();
			connection.setAutoCommit(false);
			
			cp.create(computer);

			Date now = Calendar.getInstance().getTime();

			Log log = new Log();
			log.setDescription("Insert computer");
			log.setComputerId(computer.getId());
			log.setComputerName(computer.getName());
			log.setDate(now);
			
			lg.create(log);
			
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println("Échec de la création de l'ordinateur, aucune ligne ajoutée dans la table.");
		} finally {
			DataSourceFactory.INSTANCE.closeConnectionThread();
		}
	}

	public Computer findById(int id) throws DAOException {
		return cp.findById(id);
	}

	public List<Computer> list(int start, int size) throws DAOException {
		return cp.list(start, size);
	}

	public List<Computer> list(int start, int size, int sort)
			throws DAOException {
		return cp.list(start, size, sort);
	}

	public List<Computer> list(int start, int size, String search)
			throws DAOException {
		return cp.list(start, size, search);
	}

	public List<Computer> list(int start, int size, String search, int sort)
			throws DAOException {
		return cp.list(start, size, search, sort);
	}

	public int getNumberComputers() {
		return cp.getNumberComputers();
	}

	public int getNumberComputers(String search) {
		return cp.getNumberComputers(search);
	}

	public void delete(int id) throws DAOException {
		Connection connection = null;
		try {
			connection = DataSourceFactory.INSTANCE.addConnection();
			connection.setAutoCommit(false);
			
			Computer c = cp.findById(id);
			cp.delete(id);

			Date now = Calendar.getInstance().getTime();

			Log log = new Log();
			log.setDescription("Delete computer");
			log.setComputerId(id);
			log.setComputerName(c.getName());
			log.setDate(now);
			
			lg.create(log);
			
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println("Échec de la suppression de l'ordinateur, aucune ligne supprimée de la table.");
		} finally {
			DataSourceFactory.INSTANCE.closeConnectionThread();
		}
	}

	public void update(Computer oldComputer, String newName,
			String newIntroducedDate, String newDiscontinuedDate,
			int newCompanyId) throws DAOException {
		Connection connection = null;
		try {
			connection = DataSourceFactory.INSTANCE.addConnection();
			connection.setAutoCommit(false);
			
			cp.update(oldComputer, newName, newIntroducedDate, newDiscontinuedDate,
					newCompanyId);

			Date now = Calendar.getInstance().getTime();

			Log log = new Log();
			log.setDescription("Update computer");
			log.setComputerId(oldComputer.getId());
			log.setComputerName(newName);
			log.setDate(now);
			
			lg.create(log);
			
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println("Échec de la modification de l'ordinateur, aucune ligne modifiée de la table.");
		} finally {
			DataSourceFactory.INSTANCE.closeConnectionThread();
		}
	}
}

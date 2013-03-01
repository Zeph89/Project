package com.excilys.dao;


public enum DAOFactory {
	
	INSTANCE;
	
	public CompanyDAO getCy() {
		return CompanyDaoImpl.INSTANCE;
	}

	public ComputerDAO getCp() {
		return ComputerDaoImpl.INSTANCE;
	}

	public LogDAO getLg() {
		return LogDaoImpl.INSTANCE;
	}
}

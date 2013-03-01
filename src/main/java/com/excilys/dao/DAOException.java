package com.excilys.dao;

public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 5182065246351606631L;

	public DAOException(String message) {
		super(message);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}
}

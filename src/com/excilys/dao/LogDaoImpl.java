package com.excilys.dao;

import static com.excilys.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.excilys.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.beans.Computer;
import com.excilys.beans.Log;

public enum LogDaoImpl implements LogDAO {

	INSTANCE;
	
	private static final String SQL_SELECT = "SELECT id, description, computer_id, computer_name, log_date FROM log ORDER BY name";
	private static final String SQL_SELECT_BY_ID = "SELECT id, description, computer_id, computer_name, log_date FROM log WHERE id = ?";
	private static final String SQL_SELECT_BY_COMPUTER_ID = "SELECT id, description, computer_id, computer_name, log_date FROM log WHERE computer_id = ?";
	private static final String SQL_INSERT = "INSERT INTO log (description, computer_id, computer_name, log_date) VALUES (?, ?, ?, ?)";

	@Override
	public void create(Log log) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			connexion = DataSourceFactory.INSTANCE.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_INSERT, true, log.getDescription(), log.getComputerId(), log.getComputerName(), log.getDate());

			int statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException(
						"Échec de la création du log, aucune ligne ajoutée dans la table.");
			}
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				log.setId(valeursAutoGenerees.getInt(1));
			} else {
				throw new DAOException(
						"Échec de la création du log en base, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement,
					connexion);
		}
	}

	@Override
	public Log findById(int id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Log log = null;

		try {
			connexion = DataSourceFactory.INSTANCE.getConnection();
			
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BY_ID, false, id);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				log = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return log;
	}

	@Override
	public List<Log> list() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Log> logs = new ArrayList<Log>();

		try {
			connection = DataSourceFactory.INSTANCE.getConnection();
			preparedStatement = connection.prepareStatement(SQL_SELECT);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				logs.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}

		return logs;
	}

	@Override
	public List<Log> list(Computer c) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Log> logs = new ArrayList<Log>();

		try {
			connection = DataSourceFactory.INSTANCE.getConnection();
			preparedStatement = connection.prepareStatement(SQL_SELECT_BY_COMPUTER_ID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				logs.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}

		return logs;
	}
	
	private static Log map(ResultSet resultSet) throws SQLException {
		Log log = new Log();
		log.setId(resultSet.getInt("id"));
		log.setDescription(resultSet.getString("description"));
		log.setComputerId(resultSet.getInt("computer_id"));
		log.setComputerName(resultSet.getString("computer_name"));
		log.setDate(resultSet.getDate("log_date"));
		
		return log;
	}
}

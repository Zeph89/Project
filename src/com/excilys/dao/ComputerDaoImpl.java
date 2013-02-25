package com.excilys.dao;

import static com.excilys.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.excilys.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;

public class ComputerDaoImpl implements ComputerDAO {

	private static final String SQL_SELECT = "SELECT cp.id, cp.name, cp.introduced_date, cp.discontinued_date, cp.company_id, cn.id as company_id, cn.name as company_name FROM computer cp INNER JOIN company cn ON cp.company_id = cn.id ORDER BY cp.name";
	private static final String SQL_SELECT_BY_ID = "SELECT cp.id, cp.name, cp.introduced_date, cp.discontinued_date, cp.company_id, cn.id as company_id, cn.name as company_name FROM computer cp INNER JOIN company cn ON cp.company_id = cn.id WHERE cp.id = ?";
	private static final String SQL_INSERT = "INSERT INTO computer (name, introduced_date, discontinued_date, company_id) VALUES (?, ?, ?, ?)";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM computer WHERE id = ?";

	private DAOFactory daoFactory;

	public ComputerDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void create(Computer computer) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_INSERT, true, computer.getName(), computer.getIntroducedDate(),
					computer.getDiscontinuedDate(), computer.getCompany()
							.getId());

			int statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException(
						"Échec de la création de l'ordinateur, aucune ligne ajoutée dans la table.");
			}
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				computer.setId(valeursAutoGenerees.getInt(1));
			} else {
				throw new DAOException(
						"Échec de la création de l'ordinateur en base, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement,
					connexion);
		}
	}

	@Override
	public Computer findById(int id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Computer computer = null;

		try {
			connexion = daoFactory.getConnection();
			
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BY_ID, false, id);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				computer = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return computer;
	}

	@Override
	public List<Computer> list() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_SELECT);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				computers.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}

		return computers;
	}

	@Override
	public void delete(Computer computer) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_DELETE_BY_ID, true, computer.getId());
			int statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException(
						"Échec de la suppression de l'ordinateur, aucune ligne supprimée de la table.");
			} else {
				computer.setId(-1);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}

	private static Computer map(ResultSet resultSet) throws SQLException {
		Company company = new Company();
		company.setId(resultSet.getInt("company_id"));
		company.setName(resultSet.getString("company_name"));

		Computer computer = new Computer();
		computer.setId(resultSet.getInt("id"));
		computer.setName(resultSet.getString("name"));
		computer.setIntroducedDate(resultSet.getDate("introduced_date"));
		computer.setDiscontinuedDate(resultSet.getDate("discontinued_date"));
		computer.setCompany(company);

		return computer;
	}

	public DAOFactory getDaoFactory() {
		return daoFactory;
	}

	public void setDaoFactory(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
}

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

public enum CompanyDaoImpl implements CompanyDAO {
	
	INSTANCE;

	private static final String SQL_SELECT = "SELECT id, name FROM company ORDER BY name";
	private static final String SQL_SELECT_BY_ID = "SELECT id, name FROM company WHERE id = ?";
	private static final String SQL_INSERT = "INSERT INTO company (name) VALUES (?)";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM company WHERE id = ?";

	@Override
	public void create(Company company) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			connexion = DataSourceFactory.INSTANCE.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_INSERT, true, company.getName());

			int statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException(
						"Échec de la création de la company, aucune ligne ajoutée dans la table.");
			}
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				company.setId(valeursAutoGenerees.getInt(1));
			} else {
				throw new DAOException(
						"Échec de la création de la company en base, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement,
					connexion);
		}
	}

	@Override
	public Company findById(int id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;

		try {
			connexion = DataSourceFactory.INSTANCE.getConnection();
			
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BY_ID, false, id);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				company = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return company;
	}

	@Override
	public List<Company> list() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Company> companies = new ArrayList<Company>();

		try {
			connection = DataSourceFactory.INSTANCE.getConnection();
			preparedStatement = connection.prepareStatement(SQL_SELECT);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				companies.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}

		return companies;
	}

	@Override
	public void delete(int id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = DataSourceFactory.INSTANCE.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_DELETE_BY_ID, true, id);
			int statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException(
						"Échec de la suppression de la company, aucune ligne supprimée de la table.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	private static Company map(ResultSet resultSet) throws SQLException {
		Company company = new Company();
		company.setId(resultSet.getInt("id"));
		company.setName(resultSet.getString("name"));

		return company;
	}
}

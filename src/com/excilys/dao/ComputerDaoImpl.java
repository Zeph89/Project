package com.excilys.dao;

import static com.excilys.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.excilys.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;

public class ComputerDaoImpl implements ComputerDAO {

	private static final String SQL_SELECT = "SELECT cp.id, cp.name, cp.introduced_date, cp.discontinued_date, cp.company_id, cn.id as company_id, cn.name as company_name FROM computer cp LEFT JOIN company cn ON cp.company_id = cn.id ORDER BY cp.name";
	private static final String SQL_SELECT_BY_ID = "SELECT cp.id, cp.name, cp.introduced_date, cp.discontinued_date, cp.company_id, cn.id as company_id, cn.name as company_name FROM computer cp LEFT JOIN company cn ON cp.company_id = cn.id WHERE cp.id = ?";
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
			if (computer.getCompany() != null)
				preparedStatement = initialisationRequetePreparee(connexion,
						SQL_INSERT, true, computer.getName(), computer.getIntroducedDate(),
						computer.getDiscontinuedDate(), computer.getCompany()
								.getId());
			else
				preparedStatement = initialisationRequetePreparee(connexion,
						SQL_INSERT, true, computer.getName(), computer.getIntroducedDate(),
						computer.getDiscontinuedDate(), null);

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
	public List<Computer> list(String search) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();

		try {
			StringBuilder req = new StringBuilder("SELECT cp.id, cp.name, cp.introduced_date, cp.discontinued_date, cp.company_id, cn.id as company_id, cn.name as company_name FROM computer cp " +
					"LEFT JOIN company cn ON cp.company_id = cn.id WHERE UPPER(cp.name) LIKE ? ORDER BY cp.name");
			
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(req.toString());
			preparedStatement.setString(1, "%"+search.toUpperCase()+"%");
			
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
	public void delete(int id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_DELETE_BY_ID, true, id);
			int statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException(
						"Échec de la suppression de l'ordinateur, aucune ligne supprimée de la table.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	@Override
	public void update(Computer oldComputer, String newName, String newIntroducedDate, String newDiscontinuedDate, int newCompanyId) throws DAOException  {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<Integer> index = new ArrayList<Integer>();
		StringBuilder req = new StringBuilder("UPDATE computer SET ");
		
		if (oldComputer.getName().equals(newName) == false) {
			req.append("name=?");
			index.add(0);
		}
		
		String oldIntroducedDate = "";
		if (oldComputer.getIntroducedDate() != null)
			oldIntroducedDate = dateFormat.format(oldComputer.getIntroducedDate());
		
		if (oldIntroducedDate.equals(newIntroducedDate) == false) {
			if (index.size() != 0) 
				req.append(", ");
		
			req.append("introduced_date=?");
			index.add(1);
		}
		
		String oldDiscontinuedDate = "";
		if (oldComputer.getDiscontinuedDate() != null)
			oldDiscontinuedDate = dateFormat.format(oldComputer.getDiscontinuedDate());
			
		if (oldDiscontinuedDate.equals(newDiscontinuedDate) == false) {
			if (index.size() != 0) 
				req.append(", ");
			
			req.append("discontinued_date=?");
			index.add(2);
		}

		if (oldComputer.getCompany().getId() != newCompanyId) {
			if (index.size() != 0) 
				req.append(", ");
			
			req.append("company_id=?");
			index.add(3);
		}
		
		req.append(" WHERE id=?");
		
		if (index.size() != 0) {
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {
				connection = daoFactory.getConnection();
				preparedStatement = connection.prepareStatement(req.toString());
				
				for (int i=0; i<index.size(); i++) {
					if (index.get(i) == 0) {
						preparedStatement.setString(i+1, newName);
					} else if (index.get(i) == 1) {
						if (newIntroducedDate.equals("") == false)
							preparedStatement.setDate(i+1, java.sql.Date.valueOf(newIntroducedDate));
						else
							preparedStatement.setNull(i+1, Types.DATE);
					} else if (index.get(i) == 2) {
						if (newDiscontinuedDate.equals("") == false)
							preparedStatement.setDate(i+1, java.sql.Date.valueOf(newDiscontinuedDate));
						else
							preparedStatement.setNull(i+1, Types.DATE);
					} else if (index.get(i) == 3) {
						if (newCompanyId != -1)
							preparedStatement.setInt(i+1, newCompanyId);
						else
							preparedStatement.setNull(i+1, Types.INTEGER);
					}
				}
				
				preparedStatement.setInt(index.size()+1, oldComputer.getId());

				int statut = preparedStatement.executeUpdate();
				if (statut == 0) {
					throw new DAOException(
							"Échec de la modification de l'ordinateur, aucune ligne modifiée de la table.");
				}
			} catch (SQLException e) {
				throw new DAOException(e);
			} finally {
				fermeturesSilencieuses(resultSet, preparedStatement, connection);
			}
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

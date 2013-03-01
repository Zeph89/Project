package com.excilys.dao;

import static com.excilys.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.excilys.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;

public enum ComputerDaoImpl implements ComputerDAO {
	
	INSTANCE;

	private static final String SQL_SELECT = "SELECT cp.id, cp.name, cp.introduced_date, cp.discontinued_date, cp.company_id, cn.id as company_id, cn.name as company_name FROM computer cp LEFT JOIN company cn ON cp.company_id = cn.id ORDER BY UPPER(cp.name) limit ?,?";
	private static final String SQL_SELECT_BY_ID = "SELECT cp.id, cp.name, cp.introduced_date, cp.discontinued_date, cp.company_id, cn.id as company_id, cn.name as company_name FROM computer cp LEFT JOIN company cn ON cp.company_id = cn.id WHERE cp.id = ?";
	private static final String SQL_INSERT = "INSERT INTO computer (name, introduced_date, discontinued_date, company_id) VALUES (?, ?, ?, ?)";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM computer WHERE id = ?";

	public void create(Computer computer) throws DAOException, SQLException {
		Connection connection = DataSourceFactory.INSTANCE.getConnections().get();
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		if (computer.getCompany() != null)
			preparedStatement = initialisationRequetePreparee(connection,
					SQL_INSERT, true, computer.getName(), computer.getIntroducedDate(),
					computer.getDiscontinuedDate(), computer.getCompany()
							.getId());
		else
			preparedStatement = initialisationRequetePreparee(connection,
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
	}

	public Computer findById(int id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Computer computer = null;

		try {
			connexion = DataSourceFactory.INSTANCE.getConnection();
			
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

	public List<Computer> list(int start, int size) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();

		try {
			connection = DataSourceFactory.INSTANCE.getConnection();
			preparedStatement = connection.prepareStatement(SQL_SELECT);
			preparedStatement.setInt(1, start);
			preparedStatement.setInt(2, size);
			
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
	
	public List<Computer> list(int start, int size, int sort) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();
		StringBuilder req = new StringBuilder("SELECT cp.id, cp.name, cp.introduced_date, cp.discontinued_date, cp.company_id, cn.id, cn.name as company_id, cn.name as company_name FROM computer cp LEFT JOIN company cn ON cp.company_id = cn.id ORDER BY");
		
		if (sort == 1)
			req.append(" UPPER(cp.name) ASC");
		else if (sort == -1)
			req.append(" UPPER(cp.name) DESC");
		else if (sort == 2)
			req.append(" cp.introduced_date ASC");
		else if (sort == -2)
			req.append(" cp.introduced_date DESC");
		else if (sort == 3)
			req.append(" cp.discontinued_date ASC");
		else if (sort == -3)
			req.append(" cp.discontinued_date DESC");
		else if (sort == 4)
			req.append(" UPPER(cn.name) ASC");
		else if (sort == -4)
			req.append(" UPPER(cn.name) DESC");

		req.append(" limit ?,?");
		try {
			connection = DataSourceFactory.INSTANCE.getConnection();
			preparedStatement = connection.prepareStatement(req.toString());
			preparedStatement.setInt(1, start);
			preparedStatement.setInt(2, size);

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
	
	public int getNumberComputers() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		int res = 0;
		try {
			connection = DataSourceFactory.INSTANCE.getConnection();
			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT count(id) FROM computer;");
			resultSet.next();
			
			res = resultSet.getInt(1);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, statement, connection);
		}
		
		return res;
	}
	
	public List<Computer> list(int start, int size, String search) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();

		try {
			StringBuilder req = new StringBuilder("SELECT cp.id, cp.name, cp.introduced_date, cp.discontinued_date, cp.company_id, cn.id as company_id, cn.name as company_name FROM computer cp " +
					"LEFT JOIN company cn ON cp.company_id = cn.id WHERE UPPER(cp.name) LIKE ? ORDER BY UPPER(cp.name) limit ?,?");
			
			connection = DataSourceFactory.INSTANCE.getConnection();
			preparedStatement = connection.prepareStatement(req.toString());
			preparedStatement.setString(1, "%"+search.toUpperCase()+"%");
			preparedStatement.setInt(2, start);
			preparedStatement.setInt(3, size);
			
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
	
	public List<Computer> list(int start, int size, String search, int sort) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();

		StringBuilder req = new StringBuilder("SELECT cp.id, cp.name, cp.introduced_date, cp.discontinued_date, cp.company_id, cn.id, cn.name as company_id, cn.name as company_name FROM computer cp " +
				"LEFT JOIN company cn ON cp.company_id = cn.id WHERE UPPER(cp.name) LIKE ? ORDER BY ");
		if (sort == 1)
			req.append(" UPPER(cp.name) ASC");
		else if (sort == -1)
			req.append(" UPPER(cp.name) DESC");
		else if (sort == 2)
			req.append(" cp.introduced_date ASC");
		else if (sort == -2)
			req.append(" cp.introduced_date DESC");
		else if (sort == 3)
			req.append(" cp.discontinued_date ASC");
		else if (sort == -3)
			req.append(" cp.discontinued_date DESC");
		else if (sort == 4)
			req.append(" UPPER(cn.name) ASC");
		else if (sort == -4)
			req.append(" UPPER(cn.name) DESC");

		req.append(" limit ?,?");
		
		try {
			connection = DataSourceFactory.INSTANCE.getConnection();
			preparedStatement = connection.prepareStatement(req.toString());
			preparedStatement.setString(1, "%"+search.toUpperCase()+"%");
			preparedStatement.setInt(2, start);
			preparedStatement.setInt(3, size);
			
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
	
	public int getNumberComputers(String search) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		int res = 0;
		try {
			connection = DataSourceFactory.INSTANCE.getConnection();
			preparedStatement = connection.prepareStatement("SELECT COUNT(id) FROM computer WHERE UPPER(name) LIKE UPPER(?);");
			preparedStatement.setString(1, "%"+search+"%");

			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			res = resultSet.getInt(1);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}
		
		return res;
	}

	public void delete(int id) throws DAOException, SQLException {
		Connection connection = DataSourceFactory.INSTANCE.getConnections().get();
		PreparedStatement preparedStatement = null;
		preparedStatement = initialisationRequetePreparee(connection,
				SQL_DELETE_BY_ID, true, id);
		int statut = preparedStatement.executeUpdate();
		if (statut == 0) {
			throw new DAOException(
					"Échec de la suppression de l'ordinateur, aucune ligne supprimée de la table.");
		}
	}
	
	public void update(Computer oldComputer, String newName, String newIntroducedDate, String newDiscontinuedDate, int newCompanyId) throws DAOException, SQLException  {
		Connection connection = DataSourceFactory.INSTANCE.getConnections().get();
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
			PreparedStatement preparedStatement = null;
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
}
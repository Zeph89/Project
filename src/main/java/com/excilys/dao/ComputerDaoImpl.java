package com.excilys.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.beans.Computer;
import com.excilys.mapper.ComputerRowMapper;

@Repository("ComputerDaoImpl")
public class ComputerDaoImpl implements ComputerDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void create(Computer computer) {
		if (computer.getCompany() != null)
			jdbcTemplate.update("INSERT INTO computer (name, introduced_date, discontinued_date, company_id) VALUES (?, ?, ?, ?)",
			        new Object[] { computer.getName(), computer.getIntroducedDate(), computer.getDiscontinuedDate(), computer.getCompany().getId() });
		else
			jdbcTemplate.update("INSERT INTO computer (name, introduced_date, discontinued_date, company_id) VALUES (?, ?, ?, ?)",
			        new Object[] { computer.getName(), computer.getIntroducedDate(), computer.getDiscontinuedDate(), null });
	}

	public Computer findById(int id) {
		return jdbcTemplate.queryForObject("SELECT cp.id, cp.name, cp.introduced_date, cp.discontinued_date, cp.company_id, cn.id as company_id, " +
				"cn.name as company_name FROM computer cp LEFT JOIN company cn ON cp.company_id = cn.id WHERE cp.id = ?",
	            new Object[] { id },
	            new ComputerRowMapper());
	}

	public List<Computer> list(int start, int size) {
		return jdbcTemplate.query("SELECT cp.id, cp.name, cp.introduced_date, cp.discontinued_date, cp.company_id, " +
				"cn.id as company_id, cn.name as company_name FROM computer cp LEFT JOIN company cn ON cp.company_id = cn.id ORDER BY UPPER(cp.name) limit ?,?",
				new Object[] { start, size },
	            new ComputerRowMapper());
	}
	
	public List<Computer> list(int start, int size, int sort) {
		StringBuilder query = new StringBuilder("SELECT cp.id, cp.name, cp.introduced_date, cp.discontinued_date, cp.company_id, cn.id, " +
				"cn.name as company_id, cn.name as company_name FROM computer cp LEFT JOIN company cn ON cp.company_id = cn.id ORDER BY");
		
		if (sort == 1)
			query.append(" UPPER(cp.name) ASC");
		else if (sort == -1)
			query.append(" UPPER(cp.name) DESC");
		else if (sort == 2)
			query.append(" cp.introduced_date ASC");
		else if (sort == -2)
			query.append(" cp.introduced_date DESC");
		else if (sort == 3)
			query.append(" cp.discontinued_date ASC");
		else if (sort == -3)
			query.append(" cp.discontinued_date DESC");
		else if (sort == 4)
			query.append(" UPPER(cn.name) ASC");
		else if (sort == -4)
			query.append(" UPPER(cn.name) DESC");

		query.append(" limit ?,?");
		
		return jdbcTemplate.query(query.toString(),
				new Object[] { start, size },
	            new ComputerRowMapper());
	}
	
	public int getNumberComputers() {
		return jdbcTemplate.queryForInt("SELECT count(id) FROM computer");
	}
	
	public List<Computer> list(int start, int size, String search) {
		return jdbcTemplate.query("SELECT cp.id, cp.name, cp.introduced_date, cp.discontinued_date, cp.company_id, " +
				"cn.id as company_id, cn.name as company_name FROM computer cp LEFT JOIN company cn ON cp.company_id = cn.id " +
				"WHERE UPPER(cp.name) LIKE ? ORDER BY UPPER(cp.name) limit ?,?",
				new Object[] { "%"+search.toUpperCase()+"%", start, size },
	            new ComputerRowMapper());
	}
	
	public List<Computer> list(int start, int size, String search, int sort) {
		StringBuilder query = new StringBuilder("SELECT cp.id, cp.name, cp.introduced_date, cp.discontinued_date, cp.company_id, cn.id, " +
				"cn.name as company_id, cn.name as company_name FROM computer cp LEFT JOIN company cn ON cp.company_id = cn.id " +
				"WHERE UPPER(cp.name) LIKE ? ORDER BY ");
		
		if (sort == 1)
			query.append(" UPPER(cp.name) ASC");
		else if (sort == -1)
			query.append(" UPPER(cp.name) DESC");
		else if (sort == 2)
			query.append(" cp.introduced_date ASC");
		else if (sort == -2)
			query.append(" cp.introduced_date DESC");
		else if (sort == 3)
			query.append(" cp.discontinued_date ASC");
		else if (sort == -3)
			query.append(" cp.discontinued_date DESC");
		else if (sort == 4)
			query.append(" UPPER(cn.name) ASC");
		else if (sort == -4)
			query.append(" UPPER(cn.name) DESC");

		query.append(" limit ?,?");
		
		return jdbcTemplate.query(query.toString(),
				new Object[] { "%"+search.toUpperCase()+"%", start, size },
	            new ComputerRowMapper());
	}
	
	public int getNumberComputers(String search) {
		return jdbcTemplate.queryForInt("SELECT COUNT(id) FROM computer WHERE UPPER(name) LIKE UPPER(?)",
				new Object[] { "%"+search.toUpperCase()+"%" });
	}

	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM computer WHERE id = ?",
		        new Object[] { id });
	}
	
	public void update(Computer oldComputer, String newName, String newIntroducedDate, String newDiscontinuedDate, int newCompanyId)  {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<Integer> index = new ArrayList<Integer>();
		List<Object> args = new ArrayList<Object>();
		StringBuilder query = new StringBuilder("UPDATE computer SET ");
		
		if (oldComputer.getName().equals(newName) == false) {
			query.append("name=?");
			index.add(0);
		}
		
		String oldIntroducedDate = "";
		if (oldComputer.getIntroducedDate() != null)
			oldIntroducedDate = dateFormat.format(oldComputer.getIntroducedDate());
		
		if (oldIntroducedDate.equals(newIntroducedDate) == false) {
			if (index.size() != 0) 
				query.append(", ");
		
			query.append("introduced_date=?");
			index.add(1);
		}
		
		String oldDiscontinuedDate = "";
		if (oldComputer.getDiscontinuedDate() != null)
			oldDiscontinuedDate = dateFormat.format(oldComputer.getDiscontinuedDate());
			
		if (oldDiscontinuedDate.equals(newDiscontinuedDate) == false) {
			if (index.size() != 0) 
				query.append(", ");
			
			query.append("discontinued_date=?");
			index.add(2);
		}

		if (oldComputer.getCompany().getId() != newCompanyId) {
			if (index.size() != 0) 
				query.append(", ");
			
			query.append("company_id=?");
			index.add(3);
		}
		
		query.append(" WHERE id=?");
		
		if (index.size() != 0) {
			for (int i=0; i<index.size(); i++) {
				if (index.get(i) == 0) {
					args.add(newName);
				} else if (index.get(i) == 1) {
					if (newIntroducedDate.equals("") == false)
						args.add(java.sql.Date.valueOf(newIntroducedDate));
					else
						args.add(null);
				} else if (index.get(i) == 2) {
					if (newDiscontinuedDate.equals("") == false)
						args.add(java.sql.Date.valueOf(newDiscontinuedDate));
					else
						args.add(null);
				} else if (index.get(i) == 3) {
					if (newCompanyId != -1)
						args.add(newCompanyId);
					else
						args.add(null);
				}
			}
			args.add(oldComputer.getId());

			jdbcTemplate.update(query.toString(),
			        args.toArray());
		}
	}
}

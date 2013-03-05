package com.excilys.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;

@Repository("ComputerDaoImpl")
public class ComputerDaoImpl implements ComputerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void create(Computer computer) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(computer);
	}

	public Computer findById(int id) {
		return (Computer)sessionFactory.getCurrentSession().get(Computer.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Computer> list(int start, int size) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Computer.class);
		criteria.setFirstResult(start);
		criteria.setMaxResults(size);
		criteria.addOrder(Order.asc("name").ignoreCase());
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Computer> list(int start, int size, int sort) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Computer.class);
		criteria.createAlias("company", "cy", JoinType.LEFT_OUTER_JOIN);
		
		if (sort == 1)
			criteria.addOrder(Order.asc("name").ignoreCase());
		else if (sort == -1)
			criteria.addOrder(Order.desc("name").ignoreCase());
		else if (sort == 2)
			criteria.addOrder(Order.asc("introducedDate").ignoreCase());
		else if (sort == -2)
			criteria.addOrder(Order.desc("introducedDate").ignoreCase());
		else if (sort == 3)
			criteria.addOrder(Order.asc("discontinuedDate").ignoreCase());
		else if (sort == -3)
			criteria.addOrder(Order.desc("discontinuedDate").ignoreCase());
		else if (sort == 4)
			criteria.addOrder(Order.asc("cy.name").ignoreCase());
		else if (sort == -4)
			criteria.addOrder(Order.desc("cy.name").ignoreCase());
		
		criteria.setFirstResult(start);
		criteria.setMaxResults(size);

		return criteria.list();
	}
	
	public int getNumberComputers() {
		return ((Long) sessionFactory.getCurrentSession()
				.createCriteria(Computer.class)
				.setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<Computer> list(int start, int size, String search) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Computer.class);
		criteria.setFirstResult(start);
		criteria.setMaxResults(size);
		criteria.add(Restrictions.like("name", "%"+search+"%").ignoreCase()); 
		criteria.addOrder(Order.asc("name"));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Computer> list(int start, int size, String search, int sort) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Computer.class);
		criteria.createAlias("company", "cy", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.like("name", "%"+search+"%").ignoreCase()); 
		
		if (sort == 1)
			criteria.addOrder(Order.asc("name").ignoreCase());
		else if (sort == -1)
			criteria.addOrder(Order.desc("name").ignoreCase());
		else if (sort == 2)
			criteria.addOrder(Order.asc("introducedDate").ignoreCase());
		else if (sort == -2)
			criteria.addOrder(Order.desc("introducedDate").ignoreCase());
		else if (sort == 3)
			criteria.addOrder(Order.asc("discontinuedDate").ignoreCase());
		else if (sort == -3)
			criteria.addOrder(Order.desc("discontinuedDate").ignoreCase());
		else if (sort == 4)
			criteria.addOrder(Order.asc("cy.name").ignoreCase());
		else if (sort == -4)
			criteria.addOrder(Order.desc("cy.name").ignoreCase());
		
		criteria.setFirstResult(start);
		criteria.setMaxResults(size);
		
		return criteria.list();
	}
	
	public int getNumberComputers(String search) {
		return ((Long) sessionFactory.getCurrentSession()
				.createCriteria(Computer.class)
				.add(Restrictions.like("name", "%"+search+"%").ignoreCase())
				.setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
	}

	public void delete(int id) {
		Computer computer = findById(id);
		Session session = sessionFactory.getCurrentSession();
		session.delete(computer);
	}
	
	public void update(Computer oldComputer, String newName, String newIntroducedDate, String newDiscontinuedDate, Company newCompany)  {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		oldComputer.setName(newName);
		
		try {
			if (newIntroducedDate.equals(""))
				oldComputer.setIntroducedDate(null);
			else
				oldComputer.setIntroducedDate(dateFormat.parse(newIntroducedDate));
			
			if (newDiscontinuedDate.equals(""))
				oldComputer.setDiscontinuedDate(null);
			else
				oldComputer.setDiscontinuedDate(dateFormat.parse(newDiscontinuedDate));
		} catch(Exception e) {}
		
		oldComputer.setCompany(newCompany);
		
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(oldComputer);
	}
}

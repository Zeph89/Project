package com.excilys.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.beans.Log;

@Repository("LogDaoImpl")
public class LogDaoImpl implements LogDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void create(Log log) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(log);
	}
}

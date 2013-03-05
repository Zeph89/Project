package com.excilys.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.beans.Company;

@Repository("CompanyDaoImpl")
public class CompanyDaoImpl implements CompanyDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Company findById(int id) {
		return (Company)sessionFactory.getCurrentSession().get(Company.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Company> list() {
		return sessionFactory.getCurrentSession().createQuery("From Company").list();
	}
}

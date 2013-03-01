package com.excilys.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.excilys.dao.DAOFactory;

public class InitialisationDaoFactory implements ServletContextListener {

	private static final String ATT_DAO_FACTORY = "daofactory";

	private DAOFactory daoFactory;
	
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		this.daoFactory = DAOFactory.INSTANCE;
		servletContext.setAttribute(ATT_DAO_FACTORY, this.daoFactory);
	}

	public void contextDestroyed(ServletContextEvent event) {}

	
}
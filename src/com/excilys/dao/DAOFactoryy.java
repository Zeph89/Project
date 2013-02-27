package com.excilys.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactoryy {

	private static final String FICHIER_PROPERTIES = "dao.properties";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_NOM_UTILISATEUR = "user";
	private static final String PROPERTY_MOT_DE_PASSE = "password";

	private String url;
	private String username;
	private String password;

	/* package */DAOFactoryy(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public static DAOFactoryy getInstance() throws DAOConfigurationException {
		Properties properties = new Properties();
		String url;
		String driver;
		String nomUtilisateur;
		String motDePasse;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(FICHIER_PROPERTIES);

		if (fichierProperties == null) {
			throw new DAOConfigurationException("Le fichier properties "
					+ FICHIER_PROPERTIES + " est introuvable.");
		}

		try {
			properties.load(fichierProperties);
			url = properties.getProperty(PROPERTY_URL);
			driver = properties.getProperty(PROPERTY_DRIVER);
			nomUtilisateur = properties.getProperty(PROPERTY_NOM_UTILISATEUR);
			motDePasse = properties.getProperty(PROPERTY_MOT_DE_PASSE);
		} catch (FileNotFoundException e) {
			throw new DAOConfigurationException("Le fichier properties "
					+ FICHIER_PROPERTIES + " est introuvable.", e);
		} catch (IOException e) {
			throw new DAOConfigurationException(
					"Impossible de charger le fichier properties "
							+ FICHIER_PROPERTIES, e);
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new DAOConfigurationException(
					"Le driver est introuvable dans le classpath.", e);
		}

		DAOFactoryy instance = new DAOFactoryy(url, nomUtilisateur, motDePasse);
		return instance;
	}

	/* package */Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
}

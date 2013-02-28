package com.excilys.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;

public enum DataSourceFactory {

    INSTANCE;

    private static final String FICHIER_PROPERTIES = "dao.properties";
    
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_NOM_UTILISATEUR = "user";
	private static final String PROPERTY_MOT_DE_PASSE = "password";

	protected BoneCPDataSource connectionPool = null;
	
	private DataSourceFactory() {
		initialize();
	}

    public synchronized void initialize() {
    	if(connectionPool == null) {
    		Properties properties = new Properties();
			String url;
			String driver;
			String username;
			String password;
	
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			InputStream fichierProperties = classLoader
					.getResourceAsStream(FICHIER_PROPERTIES);
	
			if (fichierProperties == null) {
				throw new DAOConfigurationException("Le fichier properties "
						+ FICHIER_PROPERTIES + " est introuvable.");
			}
	
			try {
				properties.load(fichierProperties);
				url = properties.getProperty(PROPERTY_URL);
				driver = properties.getProperty(PROPERTY_DRIVER);
				username = properties.getProperty(PROPERTY_NOM_UTILISATEUR);
				password = properties.getProperty(PROPERTY_MOT_DE_PASSE);
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

	        BoneCPConfig config = new BoneCPConfig();
	        config.setJdbcUrl(url);
	        config.setUsername(username);
	        config.setPassword(password);
	        config.setMinConnectionsPerPartition(5);
	        config.setMaxConnectionsPerPartition(10);
	        config.setPartitionCount(2);

	        connectionPool = new BoneCPDataSource(config);
	    }
    }
    
    public Connection getConnection() {
    	try {
			return connectionPool.getConnection();
		} catch (SQLException e) {
			System.err.println("Error in DataSourceFactory.getConn:" + e.getMessage());
		}
   
    	return null;
    }
}

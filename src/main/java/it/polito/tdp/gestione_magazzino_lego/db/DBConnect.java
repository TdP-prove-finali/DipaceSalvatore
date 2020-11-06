package it.polito.tdp.gestione_magazzino_lego.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import it.polito.tdp.gestione_magazzino_lego.model.exception.LegoException;

public class DBConnect {

	static private final String JDBC_URL = "jdbc:mysql://localhost/lego_prova_1";

	
	
	private static DataSource ds;

	public static Connection getConnection() throws LegoException {

		if (ds == null) {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(JDBC_URL);
			config.setUsername("root");
			config.setPassword("salva_root");
		//	config.setDriverClassName("com.mysql.cj.jdbc.Driver"); 
			
			// configurazione mysql
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("preprStmtChacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			ds = new HikariDataSource(config);
		}
		try {
			return ds.getConnection();
		} catch (SQLException sqle) {
			System.out.println("Errore connessione al DB");
			throw new LegoException("Errore nella creazione della connessione: " + sqle.getMessage(), sqle);
		}
	}

	public static void closeResources(Connection c, Statement s, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (s != null) {
				s.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (c != null) {
				c.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

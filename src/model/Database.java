package model;

import java.io.FileNotFoundException;
import java.io.FileReader;

import jakarta.json.Json;
import jakarta.json.JsonReader;
import jakarta.json.JsonObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Logger;

public class Database {
	private Logger logger;

	private String conn_url;
	private String conn_usr;
	private String conn_pwd;

	private Connection conn;

	public Database() {
		logger = Logger.getLogger(Database.class.getName());

		JsonReader jsonReader = null;
		JsonObject jsonObj = null;

		try {
			logger.info("Reading configuration file");

			jsonReader = Json.createReader(new FileReader("./app_config.json"));
			jsonObj = jsonReader.readObject().getJsonObject("mysql");

			conn_url = jsonObj.getString("uri");
			conn_usr = jsonObj.getString("usr");
			conn_pwd = jsonObj.getString("pwd");

			logger.info(String.format("Connecting to database: '%s'", conn_url));
			conn = DriverManager.getConnection(conn_url, conn_usr, conn_pwd);

		} catch (FileNotFoundException e) {
			logger.severe("Could not load configuration file");
			throw new DatabaseException("Could not load configuration file", e);
		} catch (SQLException e) {
			logger.severe("Could not stablish database connection");
			throw new DatabaseConnException("Could not stablish database connection", e);
		}
	}

	public Connection getConn() {
		return conn;
	}

	public void close() {
		try {
			logger.info("Closing database");
			conn.close();
		} catch (SQLException e) {
			logger.severe("Could not close database");
			throw new DatabaseConnException("Could not close database", e);
		}
	}
}

package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dataBase {
	// Login credentials to MySQL
		private static String URL = "jdbc:mysql://localhost:3306/tibiarpggame";
		private static String USER = "root";
		private static String PASS = "";

		// Connection object used to interact with the database
		private static Connection conn;
		// Singleton instance of the database class to prevent multiple connections
		private static dataBase instance;

		// Constructor initializes database connection.
		// Attempts to connect to database using provided URL, username, and password.
		public dataBase() {
			try {
				// Establishing a connection using JDBC DriverManager
				conn = DriverManager.getConnection(URL, USER, PASS);
			} catch (SQLException ex) {
				// Print this message if error of non connection.
				System.out.println("Database Connection Creation Failed : " + ex.getMessage());
			}
		}

		/* Get connection method.
		 Will get great for executing SQL statements in my other classes.
		 */
		public Connection getConnection() {
			return conn;
		}
}

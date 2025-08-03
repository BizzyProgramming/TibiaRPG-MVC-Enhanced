package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import service.dataBase;

// This class will handle the users authentication and registration logic
// It connects to the database and performs operations on the 'users' table

public class TibiaAccountManager {
	// Database connection helper class
	private dataBase con;

	// Constructor initializes a new database connection instance
	public TibiaAccountManager() {
		// Initialize the database connection
		con = new dataBase();
	}

	/*
	 * Attempts to log in a user by checking their username and password return true
	 * if login is successful.
	 */
	public boolean login(String username, String password) {
		// SQL query to select the user by username and password
		String sql = "SELECT id FROM users WHERE username = ? AND pass = ?";
		try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
			// Setting the username and password into the query.
			ps.setString(1, username);
			ps.setString(2, password);
			// Execute the query.
			ResultSet rs = ps.executeQuery();
			// Return true if a matching user is found.
			return rs.next();
		} catch (SQLException e) {
			// Print error if something goes wrong.
			e.printStackTrace();
			// Login failed due to error.
			return false;
		}
	}

	// Returns the user ID if login is successful
	public int getUserID(String username, String password) {
		String sql = "SELECT id FROM users WHERE username = ? AND pass = ?";
		try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // not found error
	}

	// Registers a new user into the 'users' table.
	public boolean register(String username, String password) {
		// SQL query to insert a new user into the database.
		String sql = "INSERT INTO users (username, pass, created_at) VALUES (?, ?, NOW())";
		try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
			// Setting them
			ps.setString(1, username);
			ps.setString(2, password);
			// Execute the insert operation.
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
}
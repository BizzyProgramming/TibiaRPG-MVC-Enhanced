package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.dataBase;
import model.Knight;
import model.Sorcerer;
import model.TibiaCharacter;

public class TibiaCharacterManager {
	private dataBase con;

	public TibiaCharacterManager() {
		con = new dataBase();
	}

	public boolean createCharacter(int userId, String name, int level, String description, boolean isPromoted,
			double health, String vocation) {
		String sql = "INSERT INTO characters (user_id, name, level, description, is_promoted, health, vocation, created_at) VALUES (?,?,?,?,?,?,?, NOW())";
		try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
			ps.setInt(1, userId);
			ps.setString(2, name);
			ps.setInt(3, level);
			ps.setString(4, description);
			ps.setBoolean(5, isPromoted);
			ps.setDouble(6, health);
			ps.setString(7, vocation);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Get all characters for a user
	public List<TibiaCharacter> viewAllCharacters() {
		List<TibiaCharacter> characters = new ArrayList<>();
		String sql = "SELECT * FROM characters";

		try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				int level = rs.getInt("level");
				String description = rs.getString("description");
				boolean isPromoted = rs.getBoolean("is_promoted");
				double health = rs.getDouble("health");
				String vocation = rs.getString("vocation");

				TibiaCharacter character = null;
				String baseVocation = vocation.toLowerCase().replace("elite", "").replace("master ", "").trim();

				switch (baseVocation.toLowerCase()) {
				case "knight":
					character = new Knight(name, level, description, isPromoted, health);
					break;
				case "sorcerer":
					character = new Sorcerer(name, level, description, isPromoted, health);
					break;
				// Add more vocations like "druid", "paladin" if needed
				default:
					System.out.println("Unknown vocation. Skipping character: " + name);
					continue; // Skip this character if vocation is unrecognized
				}
				characters.add(character);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return characters;
	}

	// UPDATE a character (name, description, etc.)
	public boolean updateCharacter(int charId, String newName, String newDescription) {
		String sql = "UPDATE characters SET name = ?, description = ? WHERE id = ?";
		try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
			ps.setString(1, newName);
			ps.setString(2, newDescription);
			ps.setInt(3, charId);
			int rowsUpdated = ps.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// DELETE a character
	public boolean deleteCharacter(int charId) {
		String sql = "DELETE FROM characters WHERE id = ?";
		try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
			ps.setInt(1, charId);
			int rowsDeleted = ps.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}

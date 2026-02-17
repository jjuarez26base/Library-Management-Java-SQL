package com.Library.dao;

import com.Library.db.Db;
import com.Library.model.LibraryStaff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StaffDao {
    // CREATE
    public boolean create(LibraryStaff staff) {
        String sql = "INSERT INTO \"Library Staff\" (librarian_name, email) VALUES (?, ?) RETURNING librarian_id, email, working";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, staff.getName());
            ps.setString(2, staff.getEmail());

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                staff.setId(rs.getInt("librarian_id"));
                staff.setEmail(rs.getString("email"));
                staff.setWorking(rs.getBoolean("working"));
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating staff member.", e);
        }
    }

    // READ (all)
    public List<LibraryStaff> findAll() {
        String sql = "SELECT librarian_id, librarian_name, email, working FROM \"Library Staff\" ORDER BY librarian_id";
        List<LibraryStaff> results = new ArrayList<>();

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;

        } catch (SQLException e) {
            throw new RuntimeException("Error reading library staff.", e);
        }
    }

    // READ (by id)
    public Optional<LibraryStaff> findById(int id) {
        String sql = "SELECT librarian_id, librarian_name, email, working FROM \"Library Staff\" WHERE librarian_id = ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding staff member by id.", e);
        }
    }

    // UPDATE
    public boolean update(LibraryStaff staff) {
        String sql = "UPDATE \"Library Staff\" SET librarian_name = ?, email = ?, working = ? WHERE librarian_id = ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, staff.getName());
            ps.setString(2, staff.getEmail());
            ps.setBoolean(3, staff.isWorking());
            ps.setInt(4, staff.getId());

            int rows = ps.executeUpdate();
            return rows == 1;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating staff member.", e);
        }
    }

    // DELETE
    public boolean deleteById(int id) {
        String sql = "DELETE FROM \"Library Staff\" WHERE librarian_id = ?";
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            return rows == 1;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting staff member.", e);
        }
    }

    private LibraryStaff mapRow(ResultSet rs) throws SQLException {
        return new LibraryStaff(
                rs.getInt("librarian_id"),
                rs.getString("librarian_name"),
                rs.getString("email"),
                rs.getBoolean("working")
        );
    }
}

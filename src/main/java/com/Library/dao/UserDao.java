package com.Library.dao;

import com.Library.db.Db;
import com.Library.model.LibraryUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {
    // CREATE
    public boolean create(LibraryUser user) {
        String sql = "INSERT INTO \"Library Users\" (user_name) VALUES (?) RETURNING user_id, is_user_member, books_checked_out, made_member_by_librarian";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUserName());

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                user.setUserId(rs.getInt("user_id"));
                user.setIsUserMember(rs.getBoolean("is_user_member"));
                user.setBooksCheckedOut(rs.getInt("books_checked_out"));
                user.setMadeMemberByLibrarian(rs.getInt("made_member_by_librarian"));
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user.", e);
        }
    }

    // READ (all)
    public List<LibraryUser> findAll() {
        String sql = "SELECT user_id, user_name, is_user_member, books_checked_out, made_member_by_librarian FROM \"Library Users\" ORDER BY user_id";
        List<LibraryUser> results = new ArrayList<>();

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;

        } catch (SQLException e) {
            throw new RuntimeException("Error reading users.", e);
        }
    }

    // READ (by id)
    public Optional<LibraryUser> findById(int id) {
        String sql = "SELECT user_id, user_name, is_user_member, books_checked_out, made_member_by_librarian FROM \"Library Users\" WHERE user_id = ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by id.", e);
        }
    }

    // UPDATE
    public boolean update(LibraryUser user) {
        String sql = "UPDATE \"Library Users\" SET user_name = ?, is_user_member = ?, books_checked_out = ?, made_member_by_librarian = ? WHERE user_id = ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUserName());
            if (user.isUserMember()) {
                ps.setBoolean(2, user.isUserMember());
            } else {
                ps.setBoolean(2, false);
            }
            ps.setInt(3, user.getBooksCheckedOut());
            if (user.getMadeMemberByLibrarian() > 0) {
                ps.setInt(4, user.getMadeMemberByLibrarian());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.setInt(5, user.getUserId());

            int rows = ps.executeUpdate();
            return rows == 1;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating user.", e);
        }
    }

    // DELETE
    public boolean deleteById(int id) {
        String sql = "DELETE FROM \"Library Users\" WHERE user_id = ?";
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            return rows == 1;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user.", e);
        }
    }

    private LibraryUser mapRow(ResultSet rs) throws SQLException {
        return new LibraryUser(
                rs.getInt("user_id"),
                rs.getString("user_name"),
                rs.getBoolean("is_user_member"),
                rs.getInt("books_checked_out"),
                rs.getInt("made_member_by_librarian")
        );
    }
}

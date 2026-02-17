package com.Library.dao;

import com.Library.db.Db;
import com.Library.model.LibraryBook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDao {
    // CREATE
    public boolean create(LibraryBook book) {
        String sql = "INSERT INTO \"Library Books\" (book_name) VALUES (?) RETURNING book_id, checked_out, allowed_by_librarian";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getBookName());

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                book.setId(rs.getInt("book_id"));
                book.setBookCheckedOut(rs.getBoolean("checked_out"));
                book.setAllowedByLibrarian(rs.getInt("allowed_by_librarian"));
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating book.", e);
        }
    }

    // READ (all)
    public List<LibraryBook> findAll() {
        String sql = "SELECT book_id, book_name, checked_out, \"current_user\", allowed_by_librarian FROM \"Library Books\" ORDER BY book_id";
        List<LibraryBook> results = new ArrayList<>();

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;

        } catch (SQLException e) {
            throw new RuntimeException("Error reading books.", e);
        }
    }

    // READ (by id)
    public Optional<LibraryBook> findById(int id) {
        String sql = "SELECT book_id, book_name, checked_out, \"current_user\", allowed_by_librarian FROM \"Library Books\" WHERE book_id = ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding book by id.", e);
        }
    }

    // UPDATE
    public boolean update(LibraryBook book) {
        String sql = "UPDATE \"Library Books\" SET book_name = ?, checked_out = ?,  \"current_user\" = ?, allowed_by_librarian = ? WHERE book_id = ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getBookName());
            if (book.isBookCheckedOut().isPresent()) {
                ps.setBoolean(2, book.isBookCheckedOut().get());
            } else {
                ps.setBoolean(2, false);
            }

            if (book.isCurrentUserPresent()) {
                ps.setInt(3, book.getCurrentUser());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            if (book.getAllowedByLibrarian().isPresent() && book.isLibrarianPresent()) {
                ps.setInt(4, book.getAllowedByLibrarian().get());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setInt(5, book.getId());

            int rows = ps.executeUpdate();
            return rows == 1;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating book.", e);
        }
    }

    // DELETE
    public boolean deleteById(int id) {
        String sql = "DELETE FROM \"Library Books\" WHERE book_id = ?";
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            return rows == 1;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting book.", e);
        }
    }

    private LibraryBook mapRow(ResultSet rs) throws SQLException {
        return new LibraryBook(
                rs.getInt("book_id"),
                rs.getString("book_name"),
                rs.getBoolean("checked_out"),
                rs.getInt("current_user"),
                rs.getInt("allowed_by_librarian")
        );
    }
}

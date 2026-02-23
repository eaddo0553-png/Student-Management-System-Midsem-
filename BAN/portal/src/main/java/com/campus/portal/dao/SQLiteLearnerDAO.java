package com.campus.portal.dao;

import com.campus.portal.model.Learner;
import com.campus.portal.util.DatabaseHelper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLiteLearnerDAO implements LearnerDAO {

    @Override
    public void insert(Learner learner) {

        String sql = """
                INSERT INTO learners
                (reg_number, name, course, year_level, average_score,
                 email, contact, created_on, status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, learner.getRegNumber());
            ps.setString(2, learner.getName());
            ps.setString(3, learner.getCourse());
            ps.setInt(4, learner.getYearLevel());
            ps.setDouble(5, learner.getAverageScore());
            ps.setString(6, learner.getEmailAddress());
            ps.setString(7, learner.getContactNumber());
            ps.setString(8, learner.getCreatedOn().toString());
            ps.setString(9, learner.getEnrollmentStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Insert failed", e);
        }
    }

    @Override
    public void update(Learner learner) {

        String sql = """
                UPDATE learners SET
                    name = ?,
                    course = ?,
                    year_level = ?,
                    average_score = ?,
                    email = ?,
                    contact = ?,
                    status = ?
                WHERE reg_number = ?
                """;

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, learner.getName());
            ps.setString(2, learner.getCourse());
            ps.setInt(3, learner.getYearLevel());
            ps.setDouble(4, learner.getAverageScore());
            ps.setString(5, learner.getEmailAddress());
            ps.setString(6, learner.getContactNumber());
            ps.setString(7, learner.getEnrollmentStatus());
            ps.setString(8, learner.getRegNumber());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Update failed", e);
        }
    }

    @Override
    public void delete(String regNumber) {

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM learners WHERE reg_number = ?")) {

            ps.setString(1, regNumber);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Delete failed", e);
        }
    }

    @Override
    public Optional<Learner> findByRegNumber(String regNumber) {

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM learners WHERE reg_number = ?")) {

            ps.setString(1, regNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Learner> findAll() {

        List<Learner> list = new ArrayList<>();

        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM learners")) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public List<Learner> search(String keyword) {

        List<Learner> list = new ArrayList<>();

        String sql = """
                SELECT * FROM learners
                WHERE reg_number LIKE ? OR name LIKE ?
                """;

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String value = "%" + keyword + "%";
            ps.setString(1, value);
            ps.setString(2, value);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    private Learner map(ResultSet rs) throws SQLException {

        Learner l = new Learner();
        l.setRegNumber(rs.getString("reg_number"));
        l.setName(rs.getString("name"));
        l.setCourse(rs.getString("course"));
        l.setYearLevel(rs.getInt("year_level"));
        l.setAverageScore(rs.getDouble("average_score"));
        l.setEmailAddress(rs.getString("email"));
        l.setContactNumber(rs.getString("contact"));
        l.setCreatedOn(LocalDate.parse(rs.getString("created_on")));
        l.setEnrollmentStatus(rs.getString("status"));

        return l;
    }
}
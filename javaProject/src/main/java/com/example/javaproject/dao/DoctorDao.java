package com.example.javaproject.dao;

import com.example.javaproject.entities.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDao {
    private Connection conn;

    public DoctorDao(Connection conn) {
        this.conn = conn;
    }

    public int insertDoctor(Doctor doctor) throws SQLException {
        String sql = "insert into doctor (name, category, role, password) values (?,?,?,?);";
        int result = 0;

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, doctor.getName());
        pstm.setString(2, doctor.getCategory());
        pstm.setString(3, doctor.getRole());
        pstm.setString(4, doctor.getPassword());
        result = pstm.executeUpdate();
        return result;
    }
    public Doctor findDoctorById(int id) throws SQLException {
        String sql = "Select * from doctor where id = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            String name = rs.getString("name");
            String category = rs.getString("category");
            String role = rs.getString("role");
            String password = rs.getString("password");
            Doctor doctor = new Doctor(id, name, category, role, password);
            return doctor;
        }
        return null;
    }

    public Doctor findDoctorByName(String name) throws SQLException {
        String sql = "Select * from doctor where name = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, name);
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            String category = rs.getString("category");
            String role = rs.getString("role");
            String password = rs.getString("password");
            Doctor doctor = new Doctor(id, name, category, role, password);
            return doctor;
        }
        return null;
    }

    public Doctor findDoctorByName(String name, String password) throws SQLException {
        String sql = "Select * from doctor where name = ? and password =?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, name);
        pstm.setString(2, password);
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            String category = rs.getString("category");
            String role = rs.getString("role");
            Doctor doctor = new Doctor(id, name, category, role, password);
            return doctor;
        }
        return null;
    }
}


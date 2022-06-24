package com.example.javaproject.dao;

import com.example.javaproject.entities.Doctor;
import com.example.javaproject.entities.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDao {
    private Connection conn;

    public PatientDao(Connection conn) {
        this.conn = conn;
    }

    public int insertPatient(Patient patient) throws SQLException {
        String sql = "insert into patient (name, birthday, diagnosis, status, doctor_id) values (?,?,?,?,?);";
        int result = 0;

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, patient.getName());
        pstm.setString(2, patient.getBirthday());
        pstm.setString(3, patient.getDiagnosis());
        pstm.setBoolean(4, patient.isStatus());
        pstm.setInt(5, patient.getDoctor_id());
        result = pstm.executeUpdate();
        return result;
    }
    public Patient findPatientById(int id) throws SQLException {
        String sql = "Select * from patient where id = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            String name = rs.getString("name");
            String birthday = rs.getString("birthday");
            String diagnosis = rs.getString("diagnosis");
            boolean status = rs.getBoolean("status");
            int doctor_id = rs.getInt("doctor_id");
            Patient patient = new Patient(id, name, birthday, diagnosis, status, doctor_id);
            return patient;
        }
        return null;
    }

    public Patient findPatientByName(String name) throws SQLException {
        String sql = "Select * from patient where name = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, name);
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            String birthday = rs.getString("birthday");
            String diagnosis = rs.getString("diagnosis");
            boolean status = rs.getBoolean("status");
            int doctor_id = rs.getInt("doctor_id");
            Patient patient = new Patient(id, name, birthday, diagnosis, status, doctor_id);
            return patient;
        }
        return null;
    }

    public List<Patient> queryPatients() throws SQLException {
        String sql = "select * from patient ";

        PreparedStatement pstm = conn.prepareStatement(sql);


        ResultSet rs = pstm.executeQuery();
        List<Patient> patients = new ArrayList<>();
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String birthday = rs.getString("birthday");
            String diagnosis = rs.getString("diagnosis");
            Boolean status = rs.getBoolean("status");
            int doctor_id = rs.getInt("doctor_id");
            Patient patient = new Patient(id, name, birthday, diagnosis, status, doctor_id);
            patients.add(patient);
        }
        return patients;
    }

    public List<Patient> queryPatientsByDoctorId(int doctor_id) throws SQLException {
        String sql = "select * from patient where doctor_id = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setInt(1, doctor_id);

        ResultSet rs = pstm.executeQuery();
        List<Patient> patients = new ArrayList<>();
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String birthday = rs.getString("birthday");
            String diagnosis = rs.getString("diagnosis");
            Boolean status = rs.getBoolean("status");
            Patient patient = new Patient(id, name, birthday, diagnosis, status, doctor_id);
            patients.add(patient);
        }
        return patients;
    }

    public void updatePatient(Patient patient) throws SQLException {
        String sql = "update patient set name = ?, birthday = ?, diagnosis = ?, status = ? where id = ? and doctor_id = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setString(1, patient.getName());
        pstm.setString(2, patient.getBirthday());
        pstm.setString(3, patient.getDiagnosis());
        pstm.setBoolean(4, patient.isStatus());
        pstm.setInt(5, patient.getId());
        pstm.setInt(6, patient.getDoctor_id());

        pstm.executeUpdate();
    }

    public void deletePatientById(int id) throws SQLException {
        String sql = "delete from patient where id= ?";

        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setInt(1, id);

        pstm.executeUpdate();
    }

}


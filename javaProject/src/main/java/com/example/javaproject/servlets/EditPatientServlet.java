package com.example.javaproject.servlets;

import com.example.javaproject.dao.PatientDao;
import com.example.javaproject.entities.Patient;
import com.example.javaproject.utils.MyUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "EditPatientServlet", value = "/editPatient")
public class EditPatientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request);
        PatientDao patientDao = new PatientDao(conn);

        String idStr = (String) request.getParameter("id");

        Patient patient = null;

        String errorString = null;
        int id = 0;

        try {
            id = Integer.parseInt(idStr);
            patient = patientDao.findPatientById(id);
        } catch (Exception e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        if (errorString != null && patient == null) {
            response.sendRedirect(request.getServletPath() + "/patientList");
            return;
        }

        request.setAttribute("errorString", errorString);
        request.setAttribute("patient", patient);

        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/editPatientView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request);


        PatientDao patientDao = new PatientDao(conn);

        String idStr =  request.getParameter("id");
        String name = request.getParameter("name");
        String birthday = request.getParameter("birthday");
        String diagnosis = request.getParameter("diagnosis");
        String statusStr = request.getParameter("status");
        String doctor_idStr = request.getParameter("doctor_id");

        int id = 0;
        int doctor_id = 0;
        boolean status = false;
        try {
            id = Integer.parseInt(idStr);
            doctor_id = Integer.parseInt(doctor_idStr);
            status = Boolean.parseBoolean(statusStr);
        } catch (Exception e) {
        }
        Patient patient = new Patient(id, name, birthday, diagnosis, status, doctor_id);

        String errorString = null;

        try {
            patientDao.updatePatient(patient);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }
        request.setAttribute("errorString", errorString);
        request.setAttribute("patient", patient);

        if (errorString != null) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/editPatientView.jsp");
            dispatcher.forward(request, response);
        }
        else {
            response.sendRedirect(request.getContextPath() + "/allPatients");
        }
    }
}

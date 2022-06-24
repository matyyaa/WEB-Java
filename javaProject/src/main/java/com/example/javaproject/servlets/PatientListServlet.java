package com.example.javaproject.servlets;

import com.example.javaproject.dao.PatientDao;
import com.example.javaproject.entities.Doctor;
import com.example.javaproject.entities.Patient;
import com.example.javaproject.utils.MyUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "PatientListServlet", value = "/allPatients")
public class PatientListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request);

        HttpSession session = request.getSession();
        Doctor user = MyUtils.getLoginedUser(session);
        PatientDao patientDao = new PatientDao(conn);

        String errorString = null;
        List<Patient> patients = null;
        try {
            patients = patientDao.queryPatientsByDoctorId(user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }
        request.setAttribute("errorString", errorString);
        request.setAttribute("patientList", patients);

        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/patientListView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

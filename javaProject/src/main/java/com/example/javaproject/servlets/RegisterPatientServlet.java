package com.example.javaproject.servlets;

import com.example.javaproject.dao.DoctorDao;
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
import java.sql.Date;

@WebServlet(name = "RegisterPatientServlet", value = "/registerPatient")
public class RegisterPatientServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registerPatientView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String birthday = request.getParameter("birthday");
        String diagnosis = request.getParameter("diagnosis");
        boolean status = Boolean.parseBoolean(request.getParameter("status"));

        boolean hasError = false;
        String errorString = null;

        if (name == null  || name.length() == 0) {
            hasError = true;
            errorString = "Required username!";
        } else {
            Connection conn = MyUtils.getStoredConnection(request);
            Doctor doctor = MyUtils.getLoginedUser(request.getSession());
            try {
                PatientDao patientDao = new PatientDao(conn);
                Patient patient = new Patient(name, birthday, diagnosis, status, doctor.getId());
                patientDao.insertPatient(patient);
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }
        if (hasError) {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registerPatientView.jsp");

            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/doctorPageView.jsp");

            dispatcher.forward(request, response);
        }
    }
}

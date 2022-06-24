package com.example.javaproject.servlets;

import com.example.javaproject.dao.DoctorDao;
import com.example.javaproject.entities.Doctor;
import com.example.javaproject.utils.MyUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "RegisterDoctorServlet", value = "/registerDoctor")
public class RegisterDoctorServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registerDoctorView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String password = request.getParameter("password");
        String role = "DOCTOR";

        boolean hasError = false;
        String errorString = null;

        if (name == null || password == null || name.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "Required username and password!";
        } else {
            Connection conn = MyUtils.getStoredConnection(request);
            try {
                DoctorDao doctorDao = new DoctorDao(conn);
                Doctor doctor = new Doctor(name, category, role, password);
                doctorDao.insertDoctor(doctor);
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }
        if (hasError) {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registerDoctorView.jsp");

            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/adminPageView.jsp");

            dispatcher.forward(request, response);
        }
    }
}

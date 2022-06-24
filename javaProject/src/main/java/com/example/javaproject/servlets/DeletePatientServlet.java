package com.example.javaproject.servlets;

import com.example.javaproject.dao.PatientDao;
import com.example.javaproject.utils.MyUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "DeletePatientServlet", value = "/deletePatient")
public class DeletePatientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request);
        PatientDao patientDao = new PatientDao(conn);

        String idStr = request.getParameter("id");

        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String errorString = null;

        try {
            patientDao.deletePatientById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        if (errorString != null) {
            request.setAttribute("errorString", errorString);
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/patientListView.jsp");
            dispatcher.forward(request, response);
        }
        else {
            response.sendRedirect(request.getContextPath() + "/allPatients");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

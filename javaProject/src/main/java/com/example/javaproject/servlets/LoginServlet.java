package com.example.javaproject.servlets;

import com.example.javaproject.dao.DoctorDao;
import com.example.javaproject.entities.Doctor;
import com.example.javaproject.utils.MyUtils;

import javax.print.Doc;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String rememberMeStr = request.getParameter("rememberMe");
        boolean remember = "Y".equals(rememberMeStr);

        Doctor user = null;
        boolean hasError = false;
        String errorString = null;

        if (name == null || password == null || name.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "Required name and password!";
        } else {
            Connection conn = MyUtils.getStoredConnection(request);
            try {
                user = new DoctorDao(conn).findDoctorByName(name, password);

                if (user == null) {
                    hasError = true;
                    errorString = "User Name or password invalid";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }
        if (hasError) {
            user = new Doctor();
            user.setName(name);
            user.setPassword(password);

            request.setAttribute("errorString", errorString);
            request.setAttribute("user", user);

            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");

            dispatcher.forward(request, response);
        } else {
            HttpSession session = request.getSession();
            MyUtils.storeLoginedUser(session, user);

            if (remember) {
                MyUtils.storeUserCookie(response, user);
            } else {
                MyUtils.deleteUserCookie(response);
            }

            int redirectId = -1;
            try {
                redirectId = Integer.parseInt(request.getParameter("redirectId"));
            } catch (Exception e) {
            }
            String requestUri = MyUtils.getRedirectAfterLoginUrl(request.getSession(), redirectId);
            if (requestUri != null) {
                response.sendRedirect(requestUri);
            } else {
                if(user.getRole().equals("ADMIN")) {
                    response.sendRedirect(request.getContextPath() + "/adminPage");
                }
                else if (user.getRole().equals("DOCTOR")) {
                    response.sendRedirect(request.getContextPath() + "/doctorPage");
                }
            }
        }
    }
}

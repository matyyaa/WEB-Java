package com.example.javaproject.filters;

import com.example.javaproject.dao.DoctorDao;
import com.example.javaproject.utils.MyUtils;
import com.example.javaproject.entities.Doctor;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter(filterName = "CookieFilter", urlPatterns = { "/*" })
public class CookieFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        Doctor userInSession = MyUtils.getLoginedUser(session);

        if (userInSession != null) {
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
            chain.doFilter(request, response);
            return;
        }

        Connection conn = MyUtils.getStoredConnection(request);
        DoctorDao doctorDao = new DoctorDao(conn);

        String checked = (String) session.getAttribute("COOKIE_CHECKED");
        if (checked == null && conn != null) {
            String username = MyUtils.getUserNameInCookie(req);
            try {
                Doctor user = doctorDao.findDoctorByName(username);
                MyUtils.storeLoginedUser(session, user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
        }

        chain.doFilter(request, response);
    }

}


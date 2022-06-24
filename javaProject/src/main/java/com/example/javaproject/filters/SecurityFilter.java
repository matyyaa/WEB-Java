package com.example.javaproject.filters;

import com.example.javaproject.request.UserRoleRequestWrapper;
import com.example.javaproject.utils.MyUtils;
import com.example.javaproject.utils.SecurityUtils;
import com.example.javaproject.entities.Doctor;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String servletPath = request.getServletPath();

        Doctor loginedUser = MyUtils.getLoginedUser(request.getSession());

        if (servletPath.equals("/login")) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest wrapRequest = request;

        if (loginedUser != null) {
            String userName = loginedUser.getName();

            String role = loginedUser.getRole();

            wrapRequest = new UserRoleRequestWrapper(userName, role, request);
        }

        if (SecurityUtils.isSecurityPage(request)) {

            if (loginedUser == null) {

                String requestUri = request.getRequestURI();

                int redirectId = MyUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);

                response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
                return;
            }

            boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
            if (!hasPermission) {

                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/WEB-INF/views/accessDeniedView.jsp");

                dispatcher.forward(request, response);
                return;
            }
        }

        chain.doFilter(wrapRequest, response);
    }
}


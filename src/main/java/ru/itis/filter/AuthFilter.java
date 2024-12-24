package ru.itis.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        if (httpServletRequest.getServletPath().equals("/")
                || httpServletRequest.getServletPath().startsWith("/static")
                || httpServletRequest.getServletPath().startsWith("/signIn")
                || httpServletRequest.getServletPath().startsWith("/signUp")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            HttpSession session = httpServletRequest.getSession(true);
            if (session.getAttribute("userId") != null) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            session.setAttribute("redirectAfterAuth", httpServletRequest.getRequestURI());
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/signUp");
        }
    }
}
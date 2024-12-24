package ru.itis.controller;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.model.User;
import ru.itis.service.user.common.api.UserService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/profile")
public class ProfilePageServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UUID userID = (UUID) req.getSession().getAttribute("userId");

        User user = userService.findById(userID);

        req.setAttribute("name", user.getName());
        req.setAttribute("surname", user.getSurname());
        req.setAttribute("email", user.getEmail());

        req.getRequestDispatcher("WEB-INF/view/pages/profile.jsp").forward(req, resp);

    }
}

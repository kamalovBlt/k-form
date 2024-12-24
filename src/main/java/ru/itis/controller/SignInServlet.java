package ru.itis.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.model.User;
import ru.itis.service.BCryptService;
import ru.itis.service.user.common.api.UserService;
import ru.itis.service.user.common.impl.BaseUserService;

import java.io.IOException;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/pages/auth/signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            User user = userService.findByEmail(email);
            if (!BCryptService.checkPassword(password, user.getPassword())) {
                req.setAttribute("error", "Invalid email or password");
                doGet(req, resp);
            }
            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getId());
            String redirectPath = (String) session.getAttribute("redirectAfterAuth");
            resp.sendRedirect(redirectPath);
        } catch (RuntimeException e) {
            req.setAttribute("error", "Error");
            System.out.println(e.getMessage());
            doGet(req, resp);
        }

    }

}

package ru.itis.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.exception.EmailAlreadyExistsException;
import ru.itis.model.User;
import ru.itis.service.user.common.api.UserService;

import java.io.IOException;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/pages/auth/signUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("first-name");
        String surname = req.getParameter("last-name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            userService.save(User.builder()
                    .name(name)
                    .surname(surname)
                    .email(email)
                    .password(password).build());
            User user = userService.findByEmail(email);
            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getId());
            String redirectPath = (String) session.getAttribute("redirectAfterAuth");
            resp.sendRedirect(redirectPath);
        } catch (EmailAlreadyExistsException e) {
            req.setAttribute("error", "Email already exists");
            req.getRequestDispatcher("WEB-INF/view/pages/auth/signUp.jsp").forward(req, resp);
        }
    }

}

package ru.itis.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itis.model.User;
import ru.itis.service.test.general.api.SaveTestService;
import ru.itis.service.test.general.impl.SaveTestServiceImpl;


import java.io.IOException;
import java.util.UUID;


@WebServlet("/create")
public class CreatePageServlet extends HttpServlet {

    private SaveTestService saveTestService;
    final static Logger logger = LogManager.getLogger(CreatePageServlet.class);


    @Override
    public void init(ServletConfig config) throws ServletException {
        saveTestService = (SaveTestService) config.getServletContext().getAttribute("saveTestService");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/pages/create.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UUID userID = (UUID) req.getSession().getAttribute("userId");
        saveTestService.save(req.getInputStream(), userID);
        logger.info("created new test by" + userID);
        resp.sendRedirect(getServletContext().getContextPath() + "/");
    }
}

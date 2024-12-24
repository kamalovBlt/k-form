package ru.itis.controller;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itis.model.Result;
import ru.itis.model.User;
import ru.itis.service.test.common.api.UserCompletedTestsService;
import ru.itis.service.test.common.impl.UserCompletedTestsServiceImpl;
import ru.itis.service.test.general.api.GetUserTestResultService;
import ru.itis.service.test.general.impl.GetUserTestResultServiceImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet("/testResult")
public class TestResultPageServlet extends HttpServlet {

    private UserCompletedTestsService userCompletedTestsService;
    private GetUserTestResultService getUserTestResultService;
    final static Logger logger = LogManager.getLogger(TestResultPageServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        userCompletedTestsService = (UserCompletedTestsService) config.getServletContext().getAttribute("userCompletedTestsService");
        getUserTestResultService =  (GetUserTestResultService) config.getServletContext().getAttribute("getUserTestResultService");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        UUID userID = user.getId();
        Result result = getUserTestResultService.getUserTestResult(userID, UUID.fromString(req.getParameter("testId")));
        req.setAttribute("score", result.getScore());
        req.setAttribute("description", result.getDescription());
        req.setAttribute("completedAt", result.getCompletedAt());
        req.getRequestDispatcher("/WEB-INF/view/pages/result.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UUID testID = UUID.fromString(req.getParameter("testId"));
        int score = Integer.parseInt(req.getParameter("score"));
        UUID userID = (UUID) req.getSession().getAttribute("userId");

        userCompletedTestsService.addRelation(userID, testID, LocalDateTime.now(), score);
        logger.info("user{} has completed test {} with score {}", userID, testID, score);

        resp.sendRedirect(getServletContext().getContextPath() + "/testResult?testId=" + testID);

    }
}

package ru.itis.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.service.test.general.api.GetUserStatsService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/stats")
public class StatsPageServlet extends HttpServlet {

    private GetUserStatsService getUserStatsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        getUserStatsService = (GetUserStatsService) config.getServletContext().getAttribute("getUserStatsService");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID userID = (UUID) req.getSession().getAttribute("userId");
        req.setAttribute("testsTake", getUserStatsService.getTakeTestsCount(userID));
        req.setAttribute("testsCreated", getUserStatsService.getCreateTestsCount(userID));
        req.getRequestDispatcher("/WEB-INF/view/pages/stats/stats.jsp").forward(req, resp);
    }
}

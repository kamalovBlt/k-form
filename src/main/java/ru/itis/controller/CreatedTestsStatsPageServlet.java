package ru.itis.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.model.User;
import ru.itis.service.test.general.api.GetUserStatsService;
import ru.itis.service.test.general.impl.GetUserStatsServiceImpl;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/stats/create")
public class CreatedTestsStatsPageServlet extends HttpServlet {

    private GetUserStatsService getUserStatsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        getUserStatsService = (GetUserStatsService) config.getServletContext().getAttribute("getUserStatsService");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UUID userID = (UUID) req.getSession().getAttribute("userId");
        req.setAttribute("CreatedTestStats", getUserStatsService.getCreatedTestStats(userID));
        req.getRequestDispatcher("/WEB-INF/view/pages/stats/createdTestsStats.jsp").forward(req, resp);

    }
}

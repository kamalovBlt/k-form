package ru.itis.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.model.Test;
import ru.itis.service.test.general.api.SearchTestsService;
import ru.itis.service.test.general.impl.BaseSearchTestsService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/tests")
public class TestsPageServlet extends HttpServlet {

    private SearchTestsService searchTestsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        searchTestsService = (SearchTestsService) config.getServletContext().getAttribute("searchTestsService");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String search = req.getParameter("search");

        List<Test> foundedTests;

        if (search != null) {
            foundedTests = searchTestsService.getTestOfUserRequest(search);
        }

        else {
            UUID userID = (UUID) req.getSession().getAttribute("userId");
            foundedTests = searchTestsService.getTestForRecommendations(userID);
        }
        req.setAttribute("foundedTests", foundedTests);
        req.getRequestDispatcher("/WEB-INF/view/pages/tests.jsp").forward(req, resp);
    }
}
